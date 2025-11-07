package com.back.domain.member.member.repository

import com.back.domain.member.member.entity.Member
import com.back.domain.member.member.entity.QMember
import com.back.domain.member.member.entity.QMember.member
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

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
    override fun findQByNicknameContaining(nickname: String): List<Member> {
        val member = QMember.member

        return jpaQuery
            .selectFrom(member)
            .where(
                member.nickname.contains(nickname)
            )
            .fetch()
    }

    override fun countQByNicknameContaining(nickname: String): Long {
        val member = QMember.member

        return jpaQuery
            .select(member.count())
            .from(member)
            .where(
                member.nickname.contains(nickname)
            )
            .fetchOne() ?: 0L
    }

    override fun existsQByNicknameContaining(nickname: String): Boolean {
        val member = QMember.member

        return jpaQuery
            .selectOne()
            .from(member)
            .where(
                member.nickname.contains(nickname)
            )
            .fetchFirst() != null
    }

    override fun findQByNicknameContaining(
        nickname: String,
        pageable: Pageable
    ): Page<Member> {
        val member = QMember.member

        //content 쿼리
        val result = jpaQuery
            .selectFrom(member)
            .where(
                member.nickname.contains(nickname))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        //totalCount 쿼리
        val totalCount = jpaQuery
            .select(member.count())
            .from(member)
            .where(
                member.nickname.contains(nickname)
            )
            .fetchOne() ?: 0L

        return PageImpl(
            result,
            pageable,
            totalCount
        )
    }


}