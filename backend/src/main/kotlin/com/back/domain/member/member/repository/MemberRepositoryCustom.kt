package com.back.domain.member.member.repository

import com.back.domain.member.member.entity.Member

//쿼리dsl 추가
interface MemberRepositoryCustom {

    fun findQById(id : Long): Member?
    fun findQByUsername(username : String) : Member?
    fun findQByIdIn(ids: List<Long>): List<Member>
    fun findQByUsernameAndNickname(username : String, nickname : String) : Member?
    fun findQByUsernameOrNickname(username : String, nickname : String) : List<Member>
    fun findQByUsernameAndEitherPasswordOrNickname(username: String, password: String, nickname: String): List<Member>
    fun findQByNicknameContaining(username: String): List<Member>

}