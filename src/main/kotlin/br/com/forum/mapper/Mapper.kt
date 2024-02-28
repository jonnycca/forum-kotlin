package br.com.forum.mapper

interface Mapper<Source, Target> {

    fun map(t: Source): Target
}
