package com.sawrose.marvelapp.core.common.utils

interface EntityMapper<E, D> {
    fun mapFromEntity(entity: E): D

    fun mapToEntity(domain: D): E

    fun mapFromEntityList(entities: List<E>): List<D> {
        return entities.mapTo(mutableListOf(), ::mapFromEntity)
    }

    fun mapFromDomainList(domainModels: List<D>): List<E> {
        return domainModels.mapTo(mutableListOf(), ::mapToEntity)
    }
}