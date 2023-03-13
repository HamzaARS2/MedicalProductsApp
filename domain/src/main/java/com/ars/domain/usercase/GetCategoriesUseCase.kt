package com.ars.domain.usercase

import com.ars.domain.repository.ICategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: ICategoryRepository
){

    operator fun invoke() =
        categoryRepository.fetchCategories()
}