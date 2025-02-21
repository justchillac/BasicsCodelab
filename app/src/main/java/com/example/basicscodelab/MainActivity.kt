package com.example.basicscodelab

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicscodelab.ui.theme.BasicsCodelabTheme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import kotlin.math.exp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {  //setContent and BasicsCodeLabTheme don't have any parentheses () associated with
            //them because their only single parameter is a lambda function
            //What I mean to say is setContent is a trailing lambda function with a single parameter

            BasicsCodelabTheme {  //This is a composable function (marked with @Composable)
//                It's typically generated when you create a new Compose project
//                It applies your app's theme including:
//
//                Colors
//                Typography
//                Shapes
//                Other theme attributes
//
//
//                Everything inside it will inherit these theme settings

                Surface(                  // A surface container using the 'background' color from the theme
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

//@Composable
//fun MyApp(modifier: Modifier = Modifier.fillMaxWidth(),
//          names: List<String> = listOf("World", "Compose")) {
////    Surface(modifier = modifier,
////        color = MaterialTheme.colorScheme.background) {
////         Greeting("Android")
////    }
//
//    Column(modifier = modifier) {
//        for(name in names){
//            Greeting(name = name)
//        }
//    }
//}
//
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier.fillMaxWidth()) {
//    Surface(modifier = modifier.padding(2.dp), color = MaterialTheme.colorScheme.primary) {
//        Column(modifier = Modifier.padding(24.dp)) {
//            Text(
//                text = "Hello"
//            )
//            Text(
//                text = name
//            )
//        }
//    }
//}

@Composable
fun MyApp(
    modifier: Modifier = Modifier
) {
    //Instead of using remember you can use rememberSaveable. This will save each state surviving
    // configuration changes (such as rotations) and process death.
    var shouldShowOnBoarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier) { if(shouldShowOnBoarding){
        OnboardingScreen(onBoardingClicked = ({shouldShowOnBoarding = false}))
    }else{
        Greetings()
    } }
}

@Composable
private fun Greetings(modifier: Modifier = Modifier,
                      names: List<String> = List(1000) { "$it" }
){
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) { items(items = names){
        name -> Greeting(name = name)
    } }
//    Column() {
//        for (name in names) {
//            Greeting(name = name)
//        }
//    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingsPreview() {
    BasicsCodelabTheme {
        Greetings()
    }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    BasicsCodelabTheme {
        MyApp(Modifier.fillMaxSize())
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    Card(modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp),
         colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ){
        CardContent(name = name)
    }
    //If you expand a list item and then either scroll the list until the item is out of view,
    // or rotate the device and then go back to the expanded item, you'll see that the item is
    // back to its initial state.
    //The solution for this is to use rememberSaveable instead of remember for the expanded state as well
//    val expanded = rememberSaveable{ mutableStateOf(false)}  //Here mutableStateOf recomposes the function
    //whereas remember preserves last state after recomposition
    //This means remember guards the state against recomposition so that the state is not reset
//    val extraPadding by animateDpAsState(
//        if(expanded.value) 48.dp else 0.dp,
//        animationSpec = spring(  //For now this animation can cause the app to crash
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        )
//    )

//    Surface(
//        color = MaterialTheme.colorScheme.primary,
//        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
//    ) {
//
//    }
}

@Composable
fun CardContent(name: String) {
    val expanded = rememberSaveable{ mutableStateOf(false)}

    Row(modifier = Modifier.padding(24.dp)
        .animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )) {
        Column(modifier = Modifier
            .weight(1f)
        ) {
            Text(text = "Hello ")
            Text(text = name,
                style = MaterialTheme.typography.headlineMedium.copy(  //copy lets you modify a predefined style
                    fontWeight = FontWeight.ExtraBold
                ))
            if(expanded.value){
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4)
                )
            }
        }
//            ElevatedButton(onClick = {expanded.value = !expanded.value}) { Text(text = if (expanded.value) "Show less" else "Show more") }
        IconButton(onClick = {expanded.value = !expanded.value}) {
            Icon(imageVector = if(expanded.value) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if(expanded.value) stringResource(R.string.show_less) else stringResource(R.string.show_more)
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "GreetingPreviewDark"
)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BasicsCodelabTheme(dynamicColor = false) {
        Greetings()
    }
}

@Composable
fun OnboardingScreen(onBoardingClicked: ()->Unit,     //By passing a function and not a state to
// OnboardingScreen we are making this composable more reusable and protecting the state from being
// mutated by other composables.

//In technical terms,this is how state hoisting works:
//1. Centralized Control: The parent component holds the state and becomes the single source of
// truth for that piece of data.
//2. Passing Down: The parent component passes the state down to the child components as props.
//3. Updating State: If a child component needs to update the state, it sends a request back up to
// the parent component, usually through a callback function, and the parent component updates the
// state accordingly.

    modifier: Modifier = Modifier) {
    // TODO: This state should be hoisted
    //shouldShowOnboarding is using a by keyword instead of the '='
    // This is a property delegate that saves you from typing .value every time.

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onBoardingClicked
        ) {
            Text("Continue")
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "OnboardingPreviewDark"
)

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTheme(dynamicColor = false) {
        OnboardingScreen(onBoardingClicked = {})
    }
}