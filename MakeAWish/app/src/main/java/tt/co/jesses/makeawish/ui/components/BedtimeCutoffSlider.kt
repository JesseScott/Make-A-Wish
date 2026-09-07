package tt.co.jesses.makeawish.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tt.co.jesses.makeawish.R
import tt.co.jesses.makeawish.helpers.CalendarHelper
import kotlin.math.roundToInt

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BedtimeCutoffSlider(
    cutoffIndex: Int,
    onCutoffIndexChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val labels = CalendarHelper.EVENING_ANGEL_TIME_LABELS
    val currentSelectedLabel = labels.getOrElse(cutoffIndex) { labels.first() }

    val animatedSliderValue by animateFloatAsState(
        targetValue = cutoffIndex.toFloat(),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "sliderAnimation"
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Header: Title and Active Until Subtitle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.bedtime_cutoff_label),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(R.string.bedtime_cutoff_subtitle, currentSelectedLabel),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = "🌙", fontSize = 20.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Time Slots Visual Grid
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                labels.forEachIndexed { index, timeLabel ->
                    val isActive = index <= cutoffIndex
                    val isCutoffBorder = index == cutoffIndex

                    val animatedAlpha by animateFloatAsState(
                        targetValue = if (isActive) 1.0f else 0.4f,
                        animationSpec = spring(stiffness = Spring.StiffnessMedium),
                        label = "cardAlpha"
                    )

                    val animatedBgColor by animateColorAsState(
                        targetValue = when {
                            isCutoffBorder -> MaterialTheme.colorScheme.primaryContainer
                            isActive -> MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                            else -> MaterialTheme.colorScheme.surface
                        },
                        label = "cardBgColor"
                    )

                    val animatedBorderColor by animateColorAsState(
                        targetValue = when {
                            isCutoffBorder -> MaterialTheme.colorScheme.primary
                            isActive -> MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                            else -> Color.Transparent
                        },
                        label = "cardBorderColor"
                    )

                    Box(
                        modifier = Modifier
                            .alpha(animatedAlpha)
                            .clip(RoundedCornerShape(12.dp))
                            .background(animatedBgColor)
                            .border(
                                width = if (isCutoffBorder) 2.dp else 1.dp,
                                color = animatedBorderColor,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { onCutoffIndexChange(index) }
                            .padding(horizontal = 10.dp, vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = if (isActive) "⭐" else "💤",
                                fontSize = 12.sp,
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            Text(
                                text = timeLabel,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                                color = if (isActive) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Cutoff Indicator Badge right after the cutoff item
                    if (index == cutoffIndex && cutoffIndex < labels.size - 1) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(2.dp)
                                    .height(28.dp)
                                    .background(MaterialTheme.colorScheme.primary)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "⛔ Bedtime",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.error,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Step Slider
            Slider(
                value = animatedSliderValue,
                onValueChange = { newValue ->
                    onCutoffIndexChange(newValue.roundToInt())
                },
                valueRange = 0f..(labels.size - 1).toFloat(),
                steps = labels.size - 2,
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    inactiveTrackColor = MaterialTheme.colorScheme.outlineVariant
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Sleep Protection Info Footer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "10:10 PM",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = stringResource(R.string.bedtime_sleep_hours),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "5:55 AM",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
