package com.octo.tvtracker.detail

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val tvShowDetailModule = module {
  viewModelOf(::TvShowDetailViewModel)
}
