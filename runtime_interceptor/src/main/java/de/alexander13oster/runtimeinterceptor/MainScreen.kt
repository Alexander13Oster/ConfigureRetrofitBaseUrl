package de.alexander13oster.runtimeinterceptor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import de.alexander13oster.runtimeinterceptor.R

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    innerPadding: PaddingValues,
) {
    val checked = viewModel
        .environment
        .collectAsState(false)
        .value

    Column(
        Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(stringResource(R.string.app_name))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Local Environment")
            Switch(
                checked = checked,
                onCheckedChange = { viewModel.changeEnvironment(it) }
            )
        }

        Button(onClick = { viewModel.fetchBreeds() }) {
            Text(stringResource(R.string.fetch_breeds))
        }
        val breedsState = viewModel.breeds
            .collectAsState()
            .value
        Text(breedsState)
    }
}