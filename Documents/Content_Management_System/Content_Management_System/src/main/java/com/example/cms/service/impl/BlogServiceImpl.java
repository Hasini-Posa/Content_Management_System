package com.example.cms.service.impl;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.dao.BlogRequest;
import com.example.cms.dto.BlogResponse;
import com.example.cms.dto.ContributionPanelResponse;
import com.example.cms.exception.BlockAlreadyExistByTitleException;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.exception.InvalidPanelExceptiion;
import com.example.cms.exception.TopicNotSpecifiedException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.model.Blog;
import com.example.cms.model.ContributionPanel;
import com.example.cms.model.User;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.ContributionalPanelRepo;
import com.example.cms.repository.UserRepository;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;

@Service
public class BlogServiceImpl implements BlogService{
 
	
	private ResponseStructure<BlogResponse> responseStructure;
	
	private BlogRepository blogRepo;
	
	private UserRepository userRepo;
	
	private ContributionalPanelRepo contributionalPanelRepo;
	
	private ResponseStructure<ContributionPanelResponse> responseStructures;

	
	public BlogServiceImpl(ContributionalPanelRepo contributionalPanelRepo,ResponseStructure<BlogResponse> responseStructure, BlogRepository blogRepo,
			UserRepository userRepo) {
		this.responseStructure = responseStructure;
		this.blogRepo = blogRepo; 
		this.userRepo = userRepo;
		this.contributionalPanelRepo=contributionalPanelRepo;
	}
	
	

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> createBlog(BlogRequest blog, int userId) {
		return userRepo.findById(userId).map(user -> {
			if(blogRepo.existsByTitle(blog.getTitle()))
				throw new BlockAlreadyExistByTitleException("Title Already Present");
			
			if(blog.getTopics().length<1)
				throw new TopicNotSpecifiedException("Failed Tp Create a Blog");
			
			Blog save=blogRepo.save(mappedtoBlog(blog,new Blog(),user));
			
			user.getList().add(save);
			userRepo.save(user);
			
			return ResponseEntity.ok(responseStructure.setMessage("Blog Created Sucess").setStatusCode(HttpStatus.OK.value()).setData(mappedToBlogResponse(save,new BlogResponse())));
			
		}).orElseThrow(()-> new UserNotFoundByIdException("User Id Not Found"));
	}

	private Blog mappedtoBlog(BlogRequest blogRequest,Blog blog,User user) {
		blog.setTitle(blogRequest.getTitle());
		blog.setTopics(blogRequest.getTopics());
		blog.setAbout(blogRequest.getAbout());
		//blog.getList().add(user);
		blog.setUser(user);
		ContributionPanel save=contributionalPanelRepo.save(new ContributionPanel());
		return blog;
	}
	
	private BlogResponse mappedToBlogResponse(Blog blog,BlogResponse blogResponse) {
		blogResponse.setBlogId(blog.getBlogId());
		blogResponse.setTitle(blog.getTitle());
		blogResponse.setTopics(blog.getTopics());
		blogResponse.setAbout(blog.getAbout());
		return blogResponse;
	}
	
	@Override
	public ResponseEntity<Boolean> BlogNotFoundException(String blogTitle) {
		return new ResponseEntity<Boolean>(blogRepo.existsByTitle(blogTitle),HttpStatus.FOUND);
	}


	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> findBlogById(int blogId) {
		return blogRepo.findById(blogId).map(blog -> {
			return ResponseEntity.ok(responseStructure.setMessage("Blog Found Success").setStatusCode(HttpStatus.OK.value()).
					setData(mappedToBlogResponse(blog, new BlogResponse())));
			
		}).orElseThrow(()-> new com.example.cms.exception.BlogNotFoundException("Invalid Blog Id"));
	}


	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> updateBlogData(BlogRequest blogReq, int blogId) {
		return blogRepo.findById(blogId).map(blog -> {
			blog.setTitle(blogReq.getTitle());
			blog.setTopics(blog.getTopics());
			blog.setAbout(blogReq.getAbout());
			blog.setUser(blog.getUser());
			
			Blog save=blogRepo.save(blog);
			
			return ResponseEntity.ok(responseStructure.setMessage("Update Sucessfully").setStatusCode(HttpStatus.OK.value()).
					setData(mappedToBlogResponse(save, new BlogResponse())));
		}).orElseThrow(() -> new com.example.cms.exception.BlogNotFoundException("Invalid Exception"));
		
	}



	@Override
	public ResponseEntity<ResponseStructure<ContributionPanelResponse>> addContributors(int userId, int panelId) {
		
		 String email=SecurityContextHolder.getContext().getAuthentication().getName();
		 

 
	return	 userRepo.findByEmail(email).map(owner->{
			 return contributionalPanelRepo.findById(panelId).map(panel->{
				 if(!blogRepo.existsByUserAndContributionPanel(owner, panel))
					 throw new IllegalAccessRequestException("Failed to add Contributor");
				 return userRepo.findById(userId).map(contributor->{
					 panel.getList().add(contributor);
					 contributionalPanelRepo.save(panel);
					 return ResponseEntity.ok(responseStructures.setMessage("Added Success").setStatusCode(HttpStatus.OK.value())
							 .setData(new ContributionPanelResponse().setPanelId(panel.getPanelId())));
				 }).orElseThrow(()-> new UserNotFoundByIdException("User Not Found"));
			 
			 }).orElseThrow(()-> new InvalidPanelExceptiion("Contributor PanelId Not Found"));
			 
		 }).get();
		 }
	

 

	



	@Override
	public ResponseEntity<ResponseStructure<ContributionPanelResponse>> removeUserFromContributorPanel(int userId,
			int panelId) {
		
		 String email=SecurityContextHolder.getContext().getAuthentication().getName();
		 

		 
			return	 userRepo.findByEmail(email).map(owner->{
					 return contributionalPanelRepo.findById(panelId).map(panel->{
						 if(!blogRepo.existsByUserAndContributionPanel(owner, panel))
							 throw new IllegalAccessRequestException("Failed to delete Contributor");
						 return userRepo.findById(userId).map(contributor->{
							 panel.getList().remove(contributor);
							 contributionalPanelRepo.save(panel);
							 return ResponseEntity.ok(responseStructures.setMessage("Deleted Success").setStatusCode(HttpStatus.OK.value())
									 .setData(new ContributionPanelResponse().setPanelId(panel.getPanelId())));
						 }).orElseThrow(()-> new UserNotFoundByIdException("User Not Found"));
					 
					 }).orElseThrow(()-> new InvalidPanelExceptiion("Contributor PanelId Not Found"));
					 
				 }).get();
				 }
}
