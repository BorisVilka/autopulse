package ru.autopulse05.android.feature.laximo.data.remote.dto

data class LaximoQuickGroupDto(
    val quickgroupid: String,
    val name: String,
    val link: Boolean,
    val childs: List<LaximoQuickGroupDto>
)