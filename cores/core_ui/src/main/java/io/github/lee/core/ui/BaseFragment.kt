package io.github.lee.core.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import io.github.lee.core.vm.BaseViewModel
import io.github.lee.core.vm.data.UiEvent
import io.github.lee.core.vm.data.UiState
import io.github.lee.core.vm.err.ViewModelException
import io.github.lee.ui.databinding.UiBaseHoverBinding
import io.github.lee.ui.databinding.UiBaseLinearBinding

abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel> : Fragment() {
    private var mToast: Toast? = null

    protected val mContext: Context by lazy { requireActivity() }
    protected val vm: VM? by lazy { onCreateViewModel() }
    protected open var isHover = false

    //
    protected lateinit var toolbarFrameLayout: FrameLayout
        private set
    protected lateinit var contentFrameLayout: FrameLayout
        private set

    protected val mToolbar: Toolbar? by lazy { onCreateToolbar() }
    protected val mLoading: ViewDataBinding? by lazy { onCreateLoading() }
    protected val vb: VB? by lazy { onCreateSuccess() }
    protected val mEmpty: ViewDataBinding? by lazy { onCreateEmpty() }
    protected val mError: ViewDataBinding? by lazy { onCreateError() }


    //====
    protected abstract fun onCreateViewModel(): VM?


    protected open fun onCreateToolbar(): Toolbar? = null
    protected open fun onCreateLoading(): ViewDataBinding? = null
    protected open fun onCreateSuccess(): VB? = null
    protected open fun onCreateEmpty(): ViewDataBinding? = null
    protected open fun onCreateError(): ViewDataBinding? = null
    //=====

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm?.also { this@BaseFragment.lifecycle.addObserver(it) }
        onInitData(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (isHover) {
            val v = UiBaseHoverBinding.inflate(inflater)
            toolbarFrameLayout = v.flToolbarRoot
            toolbarFrameLayout.fitsSystemWindows = true
            contentFrameLayout = v.flContentRoot
            v
        } else {
            val v = UiBaseLinearBinding.inflate(inflater)
            toolbarFrameLayout = v.flToolbarContainer
            contentFrameLayout = v.flContentContainer
            v
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // layout for children
        if (null != mToolbar) {
            toolbarFrameLayout.removeAllViews()
            toolbarFrameLayout.addView(mToolbar)
            mToolbar?.title = ""
            val a = activity
            if (a is AppCompatActivity) {
                a.setSupportActionBar(mToolbar)
            }
            toolbarFrameLayout.visibility = View.VISIBLE
        } else {
            toolbarFrameLayout.visibility = View.GONE
        }
        mLoading?.apply {
            lifecycleOwner = this@BaseFragment
            contentFrameLayout.addView(this.root)
        }
        vb?.apply {
            lifecycleOwner = this@BaseFragment
            contentFrameLayout.addView(this.root)
        }
        mEmpty?.apply {
            lifecycleOwner = this@BaseFragment
            contentFrameLayout.addView(this.root)
        }
        mError?.apply {
            lifecycleOwner = this@BaseFragment
            contentFrameLayout.addView(this.root)
        }

        if (null != mLoading) {
            if (null == vm) {
                showLoading()
            } else {
                vm?.loading()
            }
        } else {
            if (null != vb) {
                if (null == vm) {
                    showSuccess()
                } else {
                    vm?.success()
                }
            } else {
                if (null != mEmpty) {
                    if (null == vm) {
                        showEmpty()
                    } else {
                        vm?.empty()
                    }
                } else {
                    if (null != mError) {
                        if (null == vm) {
                            showError()
                        } else {
                            vm?.error()
                        }
                    } else {//子类没有实现任何布局，隐藏
                        log("The Activity do not have any layout to show")
                        contentFrameLayout.visibility = View.INVISIBLE;
                    }
                }
            }
        }

        onObserved()
        onSetViewListener()
        onSetViewData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vm?.apply { lifecycle.removeObserver(this) }
        mLoading?.apply { lifecycleOwner = null }
        vb?.apply { lifecycleOwner = null }
        mEmpty?.apply { lifecycleOwner = null }
        mError?.apply { lifecycleOwner = null }
    }

    //===

    protected open fun onInitData(savedInstanceState: Bundle?) {}
    protected open fun onObserved() {
        vm?.apply {
            collectUiState {
                when (it) {
                    UiState.None -> log("UI state is none, Skip...")
                    is UiState.Loading -> showLoading(it.tip)
                    UiState.Success -> showSuccess()
                    is UiState.Empty -> showEmpty(it.t)
                    is UiState.Error -> showError(it.t)
                }
            }
            collectUiEvent {
                when (it) {
                    UiEvent.None -> log("UI event is none, Skip...")
                    is UiEvent.ShowProgress -> showProgress(it.tip, it.canDismiss)
                    is UiEvent.HideProgress -> hideProgress(it.runnable)
                    is UiEvent.Toast -> toast(it.msg, it.isLong)
                    UiEvent.RefreshSuccess -> refreshSuccess()
                    is UiEvent.RefreshFail -> refreshFail(it.e)
                    is UiEvent.LoadMoreSuccess -> loadMoreSuccess(it.hasMore)
                    is UiEvent.LoadMoreFail -> loadMoreFail(it.e)
                }
            }
        }

    }

    protected open fun onSetViewListener() {}
    protected open fun onSetViewData() {}


    private fun log(msg: String) {
        Log.i(javaClass.simpleName, msg)
    }

    //==========
    protected open fun showLoading(tip: String? = "") {
        if (null != mLoading) {
            mLoading?.root?.visibility = View.VISIBLE
            vb?.root?.visibility = View.GONE
            mEmpty?.root?.visibility = View.GONE
            mError?.root?.visibility = View.GONE
        }
    }

    protected open fun showSuccess() {
        if (null != vb) {
            mLoading?.root?.visibility = View.GONE
            vb?.root?.visibility = View.VISIBLE
            mEmpty?.root?.visibility = View.GONE
            mError?.root?.visibility = View.GONE
        }
    }

    protected open fun showEmpty(e: ViewModelException? = null) {
        if (null != mEmpty) {
            mLoading?.root?.visibility = View.GONE
            vb?.root?.visibility = View.GONE
            mEmpty?.root?.visibility = View.VISIBLE
            mError?.root?.visibility = View.GONE
        }
    }

    protected open fun showError(e: ViewModelException? = null) {
        if (null != mError) {
            mLoading?.root?.visibility = View.GONE
            vb?.root?.visibility = View.GONE
            mEmpty?.root?.visibility = View.GONE
            mError?.root?.visibility = View.VISIBLE
        }
    }

    protected open fun showProgress(tip: String? = null, canDismiss: Boolean? = true) {

    }

    protected open fun hideProgress(runnable: Runnable? = null) {

    }

    protected open fun toast(msg: String? = null, isLong: Boolean = false) {
        if (msg.isNullOrEmpty()) {
            return
        }
        if (null == mToast) {
            mToast =
                Toast.makeText(mContext, msg, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
        }
        mToast?.setText(msg)
        mToast?.show()
    }

    protected open fun refreshSuccess() = log("This Activity is not supported refresh")

    protected open fun refreshFail(e: ViewModelException?) =
        log("This Activity is not supported refresh")

    protected open fun loadMoreSuccess(hasMore: Boolean? = true) =
        log("This Activity is not supported load more")

    protected open fun loadMoreFail(e: ViewModelException?) =
        log("This Activity is not supported load more")

    //===
    protected fun statusBarColor(
        @ColorInt color: Int,
        isDark: Boolean = false,
        fitsSystem: Boolean = true
    ) {
        val a = activity
        if (a is AppCompatActivity) {
            val window = a.window
            WindowCompat.setDecorFitsSystemWindows(window, fitsSystem)
            window.statusBarColor = color
            WindowCompat.getInsetsController(window, window.decorView)
                .isAppearanceLightStatusBars = isDark
        }
    }

    protected fun navigationBarColor(
        @ColorInt color: Int,
        isDark: Boolean = false
    ) {
        val a = activity
        if (a is AppCompatActivity) {
            val window = a.window
            window.navigationBarColor = color
            WindowCompat.getInsetsController(window, window.decorView)
                .isAppearanceLightNavigationBars = !isDark
        }
    }


    // ====
    protected fun replaceFragment(f: Fragment) {
        childFragmentManager
            .beginTransaction()
            .replace(contentFrameLayout.id, f)
            .commitNowAllowingStateLoss()
        contentFrameLayout.visibility = View.VISIBLE
    }


}