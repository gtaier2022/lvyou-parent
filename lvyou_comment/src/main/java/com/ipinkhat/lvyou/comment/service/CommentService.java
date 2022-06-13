package com.ipinkhat.lvyou.comment.service;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ipinkhat.lvyou.comment.dao.CommentDao;
import com.ipinkhat.lvyou.common.entity.UPage;
import com.ipinkhat.lvyou.common.sevice.BaseService;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.ipinkhat.lvyou.domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;



/**
 * 服务层
 *
 * @author Administrator
 *
 */
@Service
public class CommentService extends BaseService<Comment> {

	@Autowired
	private CommentDao commentDao;


	public List<Comment> findAll() {
		return commentDao.selectList(null);
	}



	public void delete(String id) {
		commentDao.deleteById(id);
	}

	public void update(Comment comment) {
		commentDao.updateById(comment);
	}

	public Comment findOne(String id) {
		return commentDao.selectById(id);
	}

	@Override
	public IPage<Comment> findPage(int page, int size) {
		Page<Comment> commentPage = new Page<>(page, size);
		return commentDao.selectPage(commentPage,null);
	}

	@Override
	public IPage<Comment> findSearch(Map searchMap, int page, int size) {
		IPage<Comment> page1 = new Page<>(page, size);
		System.out.println(searchMap);
		return commentDao.selectPage(page1,null);
	}
	//用户添加评论
	public void add(Comment comment){

		 commentDao.insert(comment);
	}
	//按页查询全部评论
	public IPage<Comment> findAllByRoute(Integer rid, Integer currentPage, Integer pageSize){
		IPage<Comment> iPage = new Page<>(currentPage,pageSize);
		QueryWrapper<Comment> wrapper = new QueryWrapper<>();
		wrapper.eq("rid",rid).eq("look_status","0");
		return commentDao.selectPage(iPage,wrapper);
	}
	public void  delete(Integer cid){
		Comment comment = commentDao.selectById(cid);
		comment.setLookStatus("2");
		commentDao.updateById(comment);
	}

	public IPage<Comment> findAll(UPage page) {
		IPage<Comment> iPage = new Page();
		QueryWrapper<Comment> wrapper = new QueryWrapper<>();
		wrapper.eq("look_status","1");
		return commentDao.selectPage(iPage,wrapper);
	}

	public void normalComment(Integer cid) {
		Comment comment = commentDao.selectById(cid);
		//不能举报的评论
		comment.setLookStatus("3");
		commentDao.updateById(comment);
	}

	public Comment findByCid(Integer cid) {
		return  commentDao.selectById(cid);
	}
	public List<Comment> findByLookStatus(Long rid){
		QueryWrapper<Comment> wrapper3 = new QueryWrapper<>();

		wrapper3.eq("rid",rid).le("look_status","1");
		return commentDao.selectList(wrapper3);
	}
}
