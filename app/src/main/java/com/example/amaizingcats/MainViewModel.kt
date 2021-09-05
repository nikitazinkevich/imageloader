package com.example.amaizingcats

import android.app.Application
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var _catsImageList = MutableLiveData<MutableList<Picture>>()

    val pictureList: LiveData<MutableList<Picture>>
        get() = _catsImageList


    init {
        onReloadAll()
    }


    fun onAddNewPicture() {
        val lastIndex = _catsImageList.value?.lastIndex
        _catsImageList.value?.add(Picture(lastIndex!!.plus(1), ImageView(getApplication())))
    }


    fun onReloadAll() {
        viewModelScope.launch {
            reloadList()
        }

    }

    private suspend fun reloadList() {
        withContext(Dispatchers.Default) {
            val createdList = mutableListOf<Picture>()
            for (i in 0..139) {
                createdList.add(
                    i,
                    Picture(i, ImageView(getApplication()))
                )

            }
            _catsImageList.postValue(createdList)
        }
    }

    override fun onCleared() {
        viewModelScope.cancel()
    }

}