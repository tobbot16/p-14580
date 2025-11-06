package com.back.domain.member.member.repository

import com.back.standard.extentions.getOrThrow
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class MemberRepositoryTest {


    @Autowired
    private lateinit var memberRepository: MemberRepository

    @Test
    fun `findById()`(){
        val member = memberRepository.findById(1).get()

        assertThat(member.id).isEqualTo(1)
    }

        @Test
    fun `findQById()`(){
        val member = memberRepository.findQById(1).getOrThrow()
        assertThat(member.id).isEqualTo(1)
    }


}