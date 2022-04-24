package com.lbcalbums.ui

import android.content.Context
import android.content.res.Resources
import android.view.View
import com.lbcalbums.R
import com.lbcalbums.domain.model.Album
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Test the [MainActivityViewModelHelper]
 */
@RunWith(JUnit4::class)
class MainActivityViewModelHelperTest {

    @Mock
    lateinit var context: Context

    @Mock
    var resources: Resources? = null

    lateinit var mainActivityViewModelHelper: MainActivityViewModelHelper

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.mainActivityViewModelHelper = MainActivityViewModelHelper(this.context, listOf(), false, true)
    }

    @Test
    fun getProgressBarVisibilityTest_network_available() {
        Assert.assertEquals(View.VISIBLE, mainActivityViewModelHelper.getProgressBarVisibility())

        mainActivityViewModelHelper.isError = true
        Assert.assertEquals(View.GONE, mainActivityViewModelHelper.getProgressBarVisibility())

        mainActivityViewModelHelper.isError = false
        mainActivityViewModelHelper.listAlbum = listOf(Album(1, 1, "title", "url", "url"))

        Assert.assertEquals(View.GONE, mainActivityViewModelHelper.getProgressBarVisibility())
    }

    @Test
    fun getProgressBarVisibilityTest_network_not_available() {
        mainActivityViewModelHelper.isConnectedToNetwork = false
        Assert.assertEquals(View.GONE, mainActivityViewModelHelper.getProgressBarVisibility())

        mainActivityViewModelHelper.listAlbum = listOf(Album(1, 1, "title", "url", "url"))
        Assert.assertEquals(View.GONE, mainActivityViewModelHelper.getProgressBarVisibility())

        mainActivityViewModelHelper.isError = true
        Assert.assertEquals(View.GONE, mainActivityViewModelHelper.getProgressBarVisibility())

        mainActivityViewModelHelper.isError = false
    }

    @Test
    fun getMessageValue_visible() {
        mainActivityViewModelHelper.isError = true
        Assert.assertEquals(View.VISIBLE, mainActivityViewModelHelper.getMessageVisibility())

        mainActivityViewModelHelper.isError = false
        mainActivityViewModelHelper.isConnectedToNetwork = false
        Assert.assertEquals(View.VISIBLE, mainActivityViewModelHelper.getMessageVisibility())
    }

    @Test
    fun getMessageValue_not_visible() {
        Assert.assertEquals(View.GONE, mainActivityViewModelHelper.getMessageVisibility())

        mainActivityViewModelHelper.isError = false
        mainActivityViewModelHelper.isConnectedToNetwork = true
        Assert.assertEquals(View.GONE, mainActivityViewModelHelper.getMessageVisibility())
    }

    @Test
    fun getMessageValueTest() {
        mainActivityViewModelHelper.isConnectedToNetwork = false
        `when`(context.resources).thenReturn(this.resources)
        `when`(this.resources?.getString(R.string.message_no_data)).thenReturn("Aucun album à afficher")
        `when`(this.resources?.getString(R.string.error_get_albums)).thenReturn("Erreur lors du chargement des Albums")

        Assert.assertEquals("Aucun album à afficher", mainActivityViewModelHelper.getMessageValue())

        mainActivityViewModelHelper.isConnectedToNetwork = true
        mainActivityViewModelHelper.isError = true
        Assert.assertEquals("Erreur lors du chargement des Albums", mainActivityViewModelHelper.getMessageValue())
    }
}
