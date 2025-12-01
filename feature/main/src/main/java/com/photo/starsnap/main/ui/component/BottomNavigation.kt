package com.photo.starsnap.main.ui.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.photo.starsnap.main.utils.BottomNavItem
import com.photo.starsnap.main.utils.NavigationRoute.UPLOAD_ROUTE

@Composable
fun BottomNavigation(
    navHostController: NavHostController,
    bottomNavItems: List<BottomNavItem>,
    onNavigate: (String) -> Unit
) {

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        modifier = Modifier.height(70.dp),
        containerColor = Color.White
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.label,
                    )
                },
                alwaysShowLabel = true,
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    val isSelected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                    if (!isSelected) {
                        if(item.route == UPLOAD_ROUTE)
                            onNavigate("add_snap")
                        else
                            navHostController.navigate(item.route)

                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.LightGray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}