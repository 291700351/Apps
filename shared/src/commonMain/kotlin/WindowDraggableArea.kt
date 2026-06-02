import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * 跨平台窗口拖动区域组件
 * 支持 macOS、Windows、Linux
 * 
 * @param modifier 修饰符
 * @param content 内容
 */
@Composable
expect fun WindowDraggableArea(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
)
