package com.example.hammersystemspizza.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.hammersystemspizza.R
import com.example.hammersystemspizza.presentation.navigation.BottomNavItem
import com.example.hammersystemspizza.presentation.navigation.NavigationGraph
import com.example.hammersystemspizza.presentation.screens.PizzaViewModel
import com.example.hammersystemspizza.ui.theme.BottomBarColor
import com.example.hammersystemspizza.ui.theme.HammerSystemsPizzaTheme
import com.example.hammersystemspizza.ui.theme.PinkMain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PizzaApp() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val navController = rememberNavController()
    val pizzaViewModel: PizzaViewModel =
        hiltViewModel()

    Scaffold(
        topBar = { PizzaTopAppBar(scrollBehavior = scrollBehavior) },
        bottomBar = { BottomBar(navController = navController) }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavigationGraph(navController = navController, pizzaViewModel = pizzaViewModel)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PizzaTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            CityAndQrTopBar()
        },
        modifier = modifier
    )
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomNavItem.Menu,
        BottomNavItem.Profile,
        BottomNavItem.Cart,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var shouldReverse by remember { mutableStateOf(false) }

    BottomNavigation(
        backgroundColor = BottomBarColor
    ) {
        screens.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.route == screen.route
            } == true
            val color = if (isSelected) {
                PinkMain
            } else {
                LocalContentColor.current.copy(alpha = ContentAlpha.medium)
            }
            BottomNavigationItem(
                label = {
                    Text(
                        text = screen.title,
                        color = color,
                        fontSize = 12.sp
                    )
                },
                icon = {
                    when (screen) {
                        is BottomNavItem.Menu -> MenuIcon(shouldReverse)
                        is BottomNavItem.Profile -> Icon(imageVector = screen.icon, contentDescription = "Navigation Icon", modifier = Modifier.size(30.dp), tint = color)
                        is BottomNavItem.Cart -> Icon(imageVector = screen.icon, contentDescription = "Navigation Icon", modifier = Modifier.size(30.dp), tint = color)
                    }
                },
                selected = isSelected,
                onClick = {
                    shouldReverse = screen !is BottomNavItem.Menu
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun MenuIcon(shouldReverse: Boolean) {
    val composition  by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.menu_anim_icon
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        composition = composition,
        speed = if (shouldReverse) -5f else 2f,
    )

    LottieAnimation(
        composition = composition,
        progress = preloaderProgress,
        modifier = Modifier.size(30.dp)
    )

}

@Composable
fun CityAndQrTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        City(modifier = Modifier)
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.qr_code),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
        )
    }
}

@Composable
fun City(modifier: Modifier){

    var mExpanded by remember { mutableStateOf(false) }

    val mCities = listOf<String>()

    var mSelectedText by remember { mutableStateOf("") }
    var mRow by remember { mutableStateOf(Size.Zero)}

    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    mRow = coordinates.size.toSize()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Москва", fontSize = 16.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(icon,null,
                Modifier.clickable { mExpanded = !mExpanded })
        }

        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){mRow.width.toDp()})
        ) {
            mCities.forEach { label ->
                DropdownMenuItem(onClick = {
                    mSelectedText = label
                    mExpanded = false
                }) {
                    Text(text = label)
                }
            }
        }
    }
}

@Composable
fun Banners() {
    val banners = listOf(R.drawable.pizza_banner_1, R.drawable.pizza_banner_2)
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(banners) { banner ->
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .width(300.dp)
                    .height(112.dp)
            ) {
                Image(
                    painter = painterResource(banner),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Category() {
    val categories = listOf("Пицца", "Комбо", "Десерты", "Напитки", "Соусы", "Завтраки", "Другие товары")
    var selected by remember { mutableStateOf(categories[0]) }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp, top = 0.dp, bottom = 0.dp)
    ) {
        items(categories) { categorie ->
            FilterChip(
                onClick = { selected = categorie },
                label = {
                    Text(
                        text = categorie,
                        fontWeight = if (categorie == selected) FontWeight.Medium else FontWeight.Normal
                    )
                },
                selected = (categorie == selected),
                colors = FilterChipDefaults.filterChipColors(
                    labelColor = Color.LightGray,
                    containerColor = Color.White,
                    selectedLabelColor = PinkMain,
                    selectedContainerColor = PinkMain.copy(alpha = 0.5f)
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderWidth = 0.5.dp,
                    borderColor = BottomBarColor
                ),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PizzaTopAppBarPreview() {
    HammerSystemsPizzaTheme {
        PizzaTopAppBar(TopAppBarDefaults.enterAlwaysScrollBehavior())
    }
}

@Preview
@Composable
fun BannerPreview() {
    HammerSystemsPizzaTheme {
        Banners()
    }
}

@Preview
@Composable
fun CategoryPreview() {
    HammerSystemsPizzaTheme {
        Category()
    }
}