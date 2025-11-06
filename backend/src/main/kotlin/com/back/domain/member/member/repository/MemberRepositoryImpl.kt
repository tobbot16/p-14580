package com.back.domain.member.member.repository

import com.back.domain.member.member.entity.Member
import com.back.domain.member.member.entity.QMember
import com.back.domain.member.member.entity.QMember.member
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

    override fun findQByUsername(username: String): Member? {
        val member = QMember.member
        return jpaQuery
            .select(member)
            .from(member)
            .where(member.username.eq(username))
            .fetchOne()//단건조회
    }

    override fun findQByIdIn(ids: List<Long>): List<Member> {
        val member = QMember.member
        return jpaQuery
            .select(member)
            .from(member)
            .where(member.id.`in`(ids))
            .fetch()//복수건조회
    }

    override fun findQByUsernameAndNickname(
        username: String,
        nickname: String
    ): Member? {
        val member = QMember.member
        return jpaQuery
            .selectFrom(member)
            .where(member.username.eq(username)
                .and(member.nickname.eq(nickname)))
            .fetchOne()
    }

    override fun findQByUsernameOrNickname(
        username: String,
        nickname: String
    ): List<Member> {
        return jpaQuery
            .selectFrom(member)
            .where(member.username.eq(username)
                .or(member.nickname.eq(nickname)))
            .fetch()
    }

    override fun findQByUsernameAndEitherPasswordOrNickname(
        username: String,
        password: String,
        nickname: String
    ): List<Member> {
        val member = QMember.member

        return jpaQuery
            .selectFrom(member)
            .where(
                member.username.eq(username)
                    .and(
                        member.password.eq(password)
                            .or(member.nickname.eq(nickname))
                    )
            )
            .fetch()
    }

    override fun findQByNicknameContaining(username: String): List<Member> {
        val member = QMember.member
        return jpaQuery
            .selectFrom(member)
            .where(member.nickname.like("%$username%"))
            .fetch()

    }
}