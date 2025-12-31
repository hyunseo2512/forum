package com.community.demo.service;

import com.community.demo.dto.CommentDTO;
import com.community.demo.entity.Board;
import com.community.demo.entity.Comment;
import com.community.demo.repository.BoardRepository;
import com.community.demo.repository.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;


    @Override
    public long post(CommentDTO commentDTO) {
        // 저장 대상은 Entity CommentDTO => Entity로 변환
        // save()
        // 해당 comment에 board 객체를 가져와서 cmtQty + 1 업데이트
        Board board = boardRepository.findById(commentDTO.getBno())
                .orElseThrow(()-> new EntityNotFoundException());
        board.setCmtQty(board.getCmtQty() + 1);

        return commentRepository.save(convertDtoToEntity(commentDTO)).getCno();
    }

//    @Override
//    public List<CommentDTO> getList(Long bno) {
//        /* findBy ** => ** 테이블 안에 있는 모든 칼럼
//        *   select * from comment where ** = ? */
//        List<Comment> list = commentRepository.findByBno(bno);
//
//        return list.stream()
//                .map(comment -> convertEntityToDto(comment))
//                .toList();
//    }
//
//    @Override
//    public Page<CommentDTO> getList(Long bno, int page) {
//        // select * from comment where bno = #{bno}
//        // order by bno desc limit {page} , 5
//        Pageable pageable = PageRequest.of(page-1,5, Sort.by("cno").descending());
//
//        Page<Comment> list = commentRepository.findByBno(bno, pageable);
//        return list.map(this::convertEntityToDto);
//    }
//
//    @Transactional
//    @Override
//    public long modify(CommentDTO commentDTO) {
//        Comment comment = commentRepository.findById(commentDTO.getCno())
//                .orElseThrow(() -> new EntityNotFoundException("해당 댓글을 찾을 수 없습니다."));
//        comment.setContent(commentDTO.getContent());
//        return comment.getCno();
//    }
//
//    @Transactional
//    @Override
//    public void remove(long cno) {
//        Comment comment = commentRepository.findById(cno)
//                .orElseThrow(()-> new EntityNotFoundException());
//        Board board = boardRepository.findById(comment.getBno())
//                .orElseThrow(()-> new EntityNotFoundException());
//        board.setCmtQty(board.getCmtQty()-1);
//        commentRepository.deleteById(cno);
//    }
}
