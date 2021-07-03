package com.github.psm.moviedb.ui.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.psm.moviedb.db.BoxStore
import com.github.psm.moviedb.db.model.person.Person
import com.github.psm.moviedb.db.model.person.Person_
import com.github.psm.moviedb.db.model.person.movie.PersonMovieCredit
import com.github.psm.moviedb.db.model.person.movie.PersonMovieCredit_
import com.github.psm.moviedb.db.model.person.tv.PersonTvCredit
import com.github.psm.moviedb.db.model.person.tv.PersonTvCredit_
import com.github.psm.moviedb.repository.TMDBRepository
import com.github.psm.moviedb.utils.asFirstLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: TMDBRepository,
    boxStore: BoxStore
) : ViewModel() {
    private val personId = requireNotNull(savedStateHandle.get<Long?>("personId"))

    val person: LiveData<Person?> = boxStore
        .person
        .query()
        .equal(Person_.id, personId)
        .asFirstLiveData()

    val personTvCredit: LiveData<PersonTvCredit?> = boxStore
        .personTvCredit
        .query()
        .equal(PersonTvCredit_.id, personId)
        .asFirstLiveData()

    val personMovieCredit: LiveData<PersonMovieCredit?> = boxStore
        .personMovieCredit
        .query()
        .equal(PersonMovieCredit_.id, personId)
        .asFirstLiveData()

    init {
        viewModelScope.launch {
            repository.getPersonDetail(personId)
            repository.getPersonTvCredit(personId)
            repository.getPersonMovieCredit(personId)
        }
    }
}