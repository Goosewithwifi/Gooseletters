package com.example.ui.screens

import android.graphics.Typeface
import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.example.R
import com.example.ui.theme.EditorialBorder
import com.example.ui.theme.EditorialBurgundy
import com.example.ui.theme.EditorialMuted
import com.example.ui.viewmodel.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    viewModel: NewsViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val article by viewModel.selectedArticle.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.app_logo),
                                contentDescription = "Gooseletters Logo",
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(CircleShape)
                                    .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "GOOSELETTERS",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    fontFamily = FontFamily.SansSerif,
                                    letterSpacing = 0.5.sp
                                )
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = onNavigateBack,
                            modifier = Modifier.testTag("back_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Navigate back",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                )
            }
        }
    ) { innerPadding ->
        val currentArticle = article
        if (currentArticle == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No article selected.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = EditorialMuted
                )
            }
        } else {
            val fields = currentArticle.fields
            val headline = fields?.headline ?: currentArticle.webTitle
            val bodyHtml = fields?.body ?: ""
            val author = fields?.byline ?: "Gooseletters Staff"
            val thumbnail = fields?.thumbnail

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(scrollState)
                    .padding(20.dp)
                    .testTag("article_detail_container")
            ) {
                // Section Header Tag
                Text(
                    text = currentArticle.sectionName.uppercase(),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 0.5.sp,
                        fontWeight = FontWeight.ExtraBold
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Large Editorial Headline
                Text(
                    text = headline,
                    style = MaterialTheme.typography.displayLarge.copy(fontSize = 26.sp, fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Author, Publication Info, and Timestamp
                Text(
                    text = "By $author",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Text(
                    text = "Published ${formatPublicationDate(currentArticle.webPublicationDate)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = EditorialMuted,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                // Large Hero Image with large rounded corners and soft shadow
                if (!thumbnail.isNullOrEmpty()) {
                    AsyncImage(
                        model = thumbnail,
                        contentDescription = headline,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(32.dp))
                            .clip(RoundedCornerShape(32.dp))
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // HTML Body rendering with distraction-free serif typography
                if (bodyHtml.isNotEmpty()) {
                    val textColor = MaterialTheme.colorScheme.onBackground.toArgb()
                    val linkColor = EditorialBurgundy.toArgb()

                    AndroidView(
                        factory = { context ->
                            TextView(context).apply {
                                textSize = 17f
                                setLineSpacing(0f, 1.35f)
                                typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
                                movementMethod = LinkMovementMethod.getInstance()
                                setLinkTextColor(linkColor)
                            }
                        },
                        update = { textView ->
                            textView.setTextColor(textColor)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                textView.text = Html.fromHtml(bodyHtml, Html.FROM_HTML_MODE_LEGACY)
                            } else {
                                @Suppress("DEPRECATION")
                                textView.text = Html.fromHtml(bodyHtml)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp)
                            .testTag("html_body_text")
                    )
                } else {
                    Text(
                        text = "The content of this article is unavailable.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = EditorialMuted,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            }
        }
    }
}
