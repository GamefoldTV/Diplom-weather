package ru.netology.diplom_weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.netology.diplom_weather.ui.theme.DiplomweatherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DiplomweatherTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = { Box {} },
                        bottomBar = {
                            val selected = navController.currentBackStackEntryAsState().value?.destination
                                .isTopLevelDestinationInHierarchy(destination)
                            NavigationBar {
                                NavigationBarItem(
                                    selected =,
                                    onClick = { },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.book),
                                            contentDescription = stringResource(id = R.string.academic_performance)
                                        )
                                    }
                                )
                                NavigationBarItem(
                                    selected = ,
                                    onClick = {  },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.calendar),
                                            contentDescription = stringResource(id = R.string.schedule)
                                        )
                                    }
                                )
                                NavigationBarItem(
                                    selected = ,
                                    onClick = {  },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.person),
                                            contentDescription = stringResource(id = R.string.profile)
                                        )
                                    }
                                )
                            }
                        }
                    ) {
                        Children(
                            modifier = Modifier.padding(it),
                            stack = component.childStack,
                            animation = stackAnimation(fade() + scale()),
                        ) { child ->
                            when (val instance = child.instance) {
                                is Child.Profile -> HomeUi(component = instance.component)
                                is Child.Disciplines -> DisciplinesUi(component = instance.component)
                                is Child.Schedule -> ScheduleUi(component = instance.component)
                            }
                        }
                    }
                }
            }
        }
    }
}