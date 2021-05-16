package com.example.turbocareassignment.di

import com.example.turbocareassignment.api.TurboCareService
import com.example.turbocareassignment.repository.TurboCareRepo
import com.example.turbocareassignment.viewmodel.MakeViewModel
import dagger.Component

@Component(modules = [TurboCareModule::class])
interface TurboCareComponent {

    fun inject(service: TurboCareService)
    fun inject(repo: TurboCareRepo)
    fun inject(viewModel: MakeViewModel)
}