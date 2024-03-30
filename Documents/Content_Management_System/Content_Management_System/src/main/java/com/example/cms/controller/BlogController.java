package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.dao.BlogRequest;
import com.example.cms.dto.BlogResponse;
import com.example.cms.dto.ContributionPanelResponse;
import com.example.cms.model.ContributionPanel;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;

@RestController
public class BlogController {

	private BlogService blogService;

	
	public BlogController(BlogService blogService) {
		this.blogService = blogService;
	}

	@PostMapping("/users/{userId}/blogs")
	public ResponseEntity<ResponseStructure<BlogResponse>> createBlog(@RequestBody BlogRequest blogRequest,@PathVariable int userId){
		return blogService.createBlog(blogRequest, userId);
	}
	
	@GetMapping("/title/{title}/blogs")
	public ResponseEntity<Boolean> BlockNotFOundException(@PathVariable String blogTitle){
		return blogService.BlogNotFoundException(blogTitle);
	}
	
	@GetMapping("/blog/{blogId}")
	public ResponseEntity<ResponseStructure<BlogResponse>> findBlogsById(@PathVariable int blogId){
		return blogService.findBlogById(blogId);
	}
	
	@PutMapping("/blogs/{blogId}")
	public ResponseEntity<ResponseStructure<BlogResponse>> updateBlogEntity(@RequestBody BlogRequest blogReq, int blogId)
	{
	return	blogService.updateBlogData(blogReq, blogId);
	}
	
	@PutMapping("/users/{userId}/contribution-panels/{panelId}")
	public ResponseEntity<ResponseStructure<ContributionPanelResponse>> addContributors(@PathVariable int userId, @PathVariable int panelId)
	{
		return blogService.addContributors(userId,panelId);
	}

	@DeleteMapping("/users/{userId}/contribution-panels/{panelId}")
	public ResponseEntity<ResponseStructure<ContributionPanelResponse>> removeUserFromContributorPanel(@PathVariable int userId, @PathVariable int panelId)
	{
		return blogService.removeUserFromContributorPanel(userId,panelId);
	}
}
