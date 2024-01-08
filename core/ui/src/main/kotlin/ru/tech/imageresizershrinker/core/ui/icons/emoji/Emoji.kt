@file:Suppress("PrivatePropertyName")

package ru.tech.imageresizershrinker.core.ui.icons.emoji

import android.content.Context
import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EmojiEmotions
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.EmojiFoodBeverage
import androidx.compose.material.icons.outlined.EmojiNature
import androidx.compose.material.icons.outlined.EmojiObjects
import androidx.compose.material.icons.outlined.EmojiSymbols
import androidx.compose.material.icons.outlined.EmojiTransportation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import ru.tech.imageresizershrinker.core.resources.R


object Emoji

private var Emotions: List<Uri>? = null
private var Food: List<Uri>? = null
private var Nature: List<Uri>? = null
private var Objects: List<Uri>? = null
private var Events: List<Uri>? = null
private var Transportation: List<Uri>? = null
private var Symbols: List<Uri>? = null

private fun Context.listAssetFiles(
    path: String
): List<String> = assets
    .list(path)
    ?.toMutableList() ?: emptyList()

/**
 * Generates Uri of the assets path.
 * @see coil.map.FileUriMapper
 * @see coil.fetch.AssetUriFetcher
 */
private fun getFileFromAssets(
    cat: String,
    filename: String
): Uri = "file:///android_asset/svg/$cat/$filename".toUri()

@Composable
fun Emoji.allIcons(
    context: Context = LocalContext.current
): ImmutableList<Uri> = remember {
    derivedStateOf {
        initializeEmojis(context)
        (Emotions!! + Food!! + Nature!! + Objects!! + Events!! + Transportation!! + Symbols!!).toPersistentList()
    }.value
}

private fun initializeEmojis(context: Context) {
    if (
        !listOf(
            Emotions,
            Food,
            Nature,
            Objects,
            Events,
            Transportation,
            Symbols
        ).all { it != null }
    ) {
        Emotions = context
            .listAssetFiles("svg/emotions")
            .sortedWith(String.CASE_INSENSITIVE_ORDER)
            .map { getFileFromAssets("emotions", it) }
        Food = context
            .listAssetFiles("svg/food")
            .sortedWith(String.CASE_INSENSITIVE_ORDER)
            .map { getFileFromAssets("food", it) }
        Nature = context
            .listAssetFiles("svg/nature")
            .sortedWith(String.CASE_INSENSITIVE_ORDER)
            .map { getFileFromAssets("nature", it) }
        Objects = context
            .listAssetFiles("svg/objects")
            .sortedWith(String.CASE_INSENSITIVE_ORDER)
            .map { getFileFromAssets("objects", it) }
        Events = context
            .listAssetFiles("svg/events")
            .sortedWith(String.CASE_INSENSITIVE_ORDER)
            .map { getFileFromAssets("events", it) }
        Transportation = context
            .listAssetFiles("svg/transportation")
            .sortedWith(String.CASE_INSENSITIVE_ORDER)
            .map { getFileFromAssets("transportation", it) }
        Symbols = context
            .listAssetFiles("svg/symbols")
            .sortedWith(String.CASE_INSENSITIVE_ORDER)
            .map { getFileFromAssets("symbols", it) }
    }
}

@Composable
fun Emoji.allIconsCategorized(
    context: Context = LocalContext.current
): ImmutableList<EmojiData> = remember {
    derivedStateOf {
        initializeEmojis(context)
        persistentListOf(
            EmojiData(
                title = context.getString(R.string.emotions),
                icon = Icons.Outlined.EmojiEmotions,
                emojis = Emotions!!
            ),
            EmojiData(
                title = context.getString(R.string.food_and_drink),
                icon = Icons.Outlined.EmojiFoodBeverage,
                emojis = Food!!
            ),
            EmojiData(
                title = context.getString(R.string.nature_and_animals),
                icon = Icons.Outlined.EmojiNature,
                emojis = Nature!!
            ),
            EmojiData(
                title = context.getString(R.string.objects),
                icon = Icons.Outlined.EmojiObjects,
                emojis = Objects!!
            ),
            EmojiData(
                title = context.getString(R.string.activities),
                icon = Icons.Outlined.EmojiEvents,
                emojis = Events!!
            ),
            EmojiData(
                context.getString(R.string.travels_and_places),
                Icons.Outlined.EmojiTransportation,
                Transportation!!
            ),
            EmojiData(
                title = context.getString(R.string.symbols),
                icon = Icons.Outlined.EmojiSymbols,
                emojis = Symbols!!
            )
        )
    }.value
}