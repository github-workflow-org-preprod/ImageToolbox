/*
 * ImageToolbox is an image editor for android
 * Copyright (c) 2024 T8RIN (Malik Mukhametzyanov)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * You should have received a copy of the Apache License
 * along with this program.  If not, see <http://www.apache.org/licenses/LICENSE-2.0>.
 */

package ru.tech.imageresizershrinker.core.ui.widget.controls

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Fingerprint
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.tech.imageresizershrinker.core.domain.model.ImageFormat
import ru.tech.imageresizershrinker.core.resources.R
import ru.tech.imageresizershrinker.core.settings.presentation.LocalSettingsState
import ru.tech.imageresizershrinker.core.ui.widget.preferences.PreferenceRowSwitch

@Composable
fun SaveExifWidget(
    checked: Boolean,
    imageFormat: ImageFormat,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceContainer,
) {
    val settingsState = LocalSettingsState.current
    LaunchedEffect(Unit) {
        onCheckedChange(settingsState.exifWidgetInitialState)
    }
    PreferenceRowSwitch(
        modifier = modifier,
        title = stringResource(R.string.keep_exif),
        subtitle = if (imageFormat.canWriteExif) {
            stringResource(R.string.keep_exif_sub)
        } else {
            stringResource(
                R.string.image_exif_warning,
                imageFormat.title
            )
        },
        checked = checked,
        enabled = imageFormat.canWriteExif,
        changeAlphaWhenDisabled = false,
        shape = RoundedCornerShape(24.dp),
        color = backgroundColor,
        onClick = onCheckedChange,
        startIcon = Icons.Rounded.Fingerprint
    )
}