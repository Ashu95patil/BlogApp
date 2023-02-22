package com.codewithAshu.blog.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="comments")
@NoArgsConstructor
@Getter
@Setter
public class Comment {
	
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	private String content;
	
	@ManyToOne
	private Post post;

}
