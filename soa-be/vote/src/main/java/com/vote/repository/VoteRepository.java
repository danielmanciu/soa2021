package com.vote.repository;

import com.vote.dto.VoteDto;
import com.vote.dto.VoteDto;
import com.vote.entity.VoteEntity;
import com.vote.entity.VoteEntity;
import com.vote.mapper.VoteMapper;
import com.vote.mapper.VoteMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VoteRepository {
    private VoteJpaRepository voteJpaRepository;
    private VoteMapper voteMapper;

    @Autowired
    public VoteRepository(final VoteJpaRepository voteJpaRepository, final VoteMapper voteMapper) {
        this.voteJpaRepository = voteJpaRepository;
        this.voteMapper = voteMapper;
    }

    public VoteDto save(final VoteDto vote) {
        VoteEntity savedVote = voteJpaRepository.save(voteMapper.Dto2Entity(vote));
        return voteMapper.Entity2Dto(savedVote);
    }

    public VoteDto findByUserIdAndQuestionId(final Long userId, final Long questionId) {
        VoteEntity voteEntity = voteJpaRepository.findByUserIdAndQuestionId(userId, questionId);
        return voteMapper.Entity2Dto(voteEntity);
    }

    public VoteDto findVoteByQuestionId(final Long id) {
        VoteEntity voteEntity = voteJpaRepository.findByQuestionId(id);
        return voteMapper.Entity2Dto(voteEntity);
    }

    public List<VoteDto> findVotes() {
        List<VoteEntity> votes = voteJpaRepository.findAll();
        return voteMapper.EntityList2DtoList(votes);
    }

    public List<VoteDto> findAll() {
        List<VoteEntity> allVotes = voteJpaRepository.findAll();
        return voteMapper.EntityList2DtoList(allVotes);
    }

}
