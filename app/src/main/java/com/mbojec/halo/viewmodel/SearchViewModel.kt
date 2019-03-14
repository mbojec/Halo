package com.mbojec.halo.viewmodel

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.R
import com.mbojec.halo.model.SearchCityList
import com.mbojec.halo.model.Response
import com.mbojec.halo.utils.NetworkUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchViewModel @Inject constructor(val haloApplication: HaloApplication): ViewModel() {

    val responseStatus: MutableLiveData<Response> = MutableLiveData()
    val searchCityList: MutableLiveData<SearchCityList> = MutableLiveData()
    private lateinit var disposable: Disposable

    private fun searchCity(cityName: String){
        haloApplication.networkRepository.fetchCityList(cityName, responseStatus, searchCityList)
    }

    fun hideSoftKeyboard(activity: Activity){
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
    }

    fun createSearchViewObserver(searchView: SearchView){
        val searchViewTextObservable = createSearchViewObservable(searchView)
        disposable = searchViewTextObservable
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnNext {}
            .map { it.trim { it <= ' ' } }
            .subscribe {
                if (NetworkUtils.isNetworkConnected(haloApplication)){
                    searchCity(it)
                }
            }
    }

    private fun createSearchViewObservable(searchView: SearchView): Observable<String> {

        val textChangeObservable = Observable.create<String> { emitter ->
            val onQueryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrEmpty()){
                        searchCityList.postValue(null)
                    } else {
                        emitter.onNext(newText)
                    }
                    return false
                }
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { emitter.onNext(query) }
                    return false
                }
            }
            searchView.setOnQueryTextListener(onQueryTextListener)
            searchView.queryHint = haloApplication.resources.getString(R.string.city_search_vie_hint)

            emitter.setCancellable {
                searchView.setOnQueryTextListener(null)
            }
        }

        return textChangeObservable
            .filter { it.length > 2 }
            .debounce(500, TimeUnit.MILLISECONDS)
    }

}
