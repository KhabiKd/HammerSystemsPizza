package com.example.hammersystemspizza.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.hammersystemspizza.R
import com.example.hammersystemspizza.domain.model.Pizza
import com.example.hammersystemspizza.presentation.Banners
import com.example.hammersystemspizza.presentation.Category
import com.example.hammersystemspizza.ui.theme.HammerSystemsPizzaTheme
import com.example.hammersystemspizza.ui.theme.PinkMain

@Composable
fun MenuScreen(
    pizzaUiState: PizzaUiState
) {
    when(pizzaUiState) {
        is PizzaUiState.Loading -> LoadingScreen(modifier = Modifier)
        is PizzaUiState.Success -> PizzaListScreen(
            pizzas = pizzaUiState.pizzas,
            modifier = Modifier
        )
        is PizzaUiState.Error -> ErrorScreen(modifier = Modifier)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = modifier
                .size(50.dp)
                .align(Alignment.Center),
            color = PinkMain,
        )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {

}

@Composable
fun PizzaListScreen(pizzas: List<Pizza>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(top = 4.dp, bottom = 4.dp, end = 4.dp)
    ) {
        item {
            Banners()
        }
        item {
            Category()
        }
        items(pizzas) { pizza ->
            PizzaItem(pizza = pizza)
        }
    }
}

@Composable
fun PizzaItem(pizza: Pizza, modifier: Modifier = Modifier) {
    Surface {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(8.dp),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current).data(pizza.img_src)
                    .crossfade(true).build(),
                error = painterResource(R.drawable.baseline_broken_image_24),
                placeholder = painterResource(R.drawable.baseline_image_search_24),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            )

            Column(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = pizza.name,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth(),
                )
                Text(
                    text = pizza.description,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                )
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .border(1.dp, PinkMain, RoundedCornerShape(5.dp))
                        .align(Alignment.End),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.pizza_price, pizza.price),
                        style = MaterialTheme.typography.bodyLarge.copy(color = PinkMain),
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PizzaItemPreview() {
    HammerSystemsPizzaTheme {
        PizzaItem(
            pizza = Pizza(
                "Ветчина и грибы",
                "Ветчина, шампиньоны, увеличенная порция моцареллы, томатный соус",
                345,
                "https://drive.google.com/file/d/1n-XhtaIU0EJykausdNLCZ0zSgir9odSt/view?usp=sharing"
            )
        )
    }
}