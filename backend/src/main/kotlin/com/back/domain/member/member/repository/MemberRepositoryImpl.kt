package com.back.domain.member.member.repository

import com.back.domain.member.member.entity.Member
import com.back.domain.member.member.entity.QMember
import com.querydsl.jpa.impl.JPAQueryFactory

//구현
class MemberRepositoryImpl(
    private val jpaQuery: JPAQueryFactory
) : MemberRepositoryCustom {
    override fun findQById(id: Long): Member? {
        val member = QMember.member
        return jpaQuery
            .select(member)
            .from(member)
            .where(member.id.eq(id))
            .fetchOne()//단건조회
    }

}