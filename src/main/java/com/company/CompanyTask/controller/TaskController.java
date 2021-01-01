package com.company.CompanyTask.controller;


import org.json.simple.parser.*;
import org.json.simple.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.company.CompanyTask.model.AuthorModel;
import com.company.CompanyTask.model.PostAuthorModel;
import com.company.CompanyTask.model.PostModel;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin(origins="*")
public class TaskController {
	
	private static final String filePath = "store.json";
	
	@PostMapping(value="/posts")
	public JSONObject createPost(@RequestBody PostAuthorModel postAuthor) {
		JSONObject jsonObj = new JSONObject();
		try {
            // read the json file
			 JSONParser parser = new JSONParser();
	         Object obj = parser.parse(new FileReader(filePath));
	         JSONObject jsonObject = (JSONObject) obj;
	         
	         ArrayList<PostModel> postList=postAuthor.getPosts();
	         if(postList!=null && postList.size()>0) {
	         JSONArray posts = (JSONArray)jsonObject.get("posts");
	         //inserting POST
	         int i=posts.size()-1;
	         long postId=-1;
	         if(i>=0)
	         {JSONObject rec =(JSONObject)posts.get(i);
	         postId=(long) rec.get("id");}
	         else
	        	 postId = -1;
	        
	         for (PostModel post : postList) 
	         { 
	        	 post.setId(postId+1);
	        	 ObjectMapper mapper = new ObjectMapper();
	             //Converting the Object to JSONString
	             String jsonString = mapper.writeValueAsString(post);
	             JSONParser parser1 = new JSONParser();  
	             JSONObject jsonPost = (JSONObject) parser1.parse(jsonString);
	             posts.add(jsonPost);
	             postId=postId+1;
	         }
	         }
	         //inserting AUTHOR
	         ArrayList<AuthorModel> authorList=postAuthor.getAuthors();
	         if(authorList!=null && authorList.size()>0) {
	         JSONArray authors = (JSONArray)jsonObject.get("authors");
	         int i=authors.size()-1;
	         long authorId=-1;
	         if(i>=0)
	         {JSONObject rec =(JSONObject)authors.get(i);
	         authorId=(long) rec.get("id");}
	         else
	        	 authorId = -1;
	         
	         for (AuthorModel author : authorList) 
	         { 
	        	 author.setId(authorId+1);
	        	 ObjectMapper mapper = new ObjectMapper();
	             //Converting the Object to JSONString
	             String jsonString = mapper.writeValueAsString(author);
	             JSONParser parser1 = new JSONParser();  
	             JSONObject jsonAuthor = (JSONObject) parser1.parse(jsonString);
	             authors.add(jsonAuthor);
	             authorId=authorId+1;
	         }
	         
	         }
	         FileWriter file = new FileWriter(filePath);
	         file.write(jsonObject.toJSONString());
	         file.close();
	         
	         jsonObject.put("status",true);
	         jsonObject.put("statusCode",200);
	         return jsonObject;
		} catch (FileNotFoundException ex) {

			jsonObj.put("status",false);
			jsonObj.put("statusCode",404);
			jsonObj.put("error", "File Not found");
            ex.printStackTrace();
            
        } catch (IOException ex) {
        	jsonObj.put("status",false);
			jsonObj.put("statusCode",500);
        	jsonObj.put("error", "Something Went Wrong");
            ex.printStackTrace();
        } catch (Exception ex) {
        	jsonObj.put("status",false);
			jsonObj.put("statusCode",500);
        	jsonObj.put("error", "Something Went Wrong");
            ex.printStackTrace();
        }
		
		return jsonObj;
		
	}

	@GetMapping(value="/posts/{Id}")
	public JSONObject getPosts(@PathVariable("Id") String id) {
		JSONObject jsonObj = new JSONObject();
			try {
	            // read the json file
				 JSONParser parser = new JSONParser();
		         Object obj = parser.parse(new FileReader(filePath));
		         JSONObject jsonObject = (JSONObject) obj;
		         JSONArray posts = (JSONArray)jsonObject.get("posts");
				for(int i = 0; i < posts.size(); i++)
				{
				      JSONObject objects = (JSONObject)posts.get(i);
				      if((long)objects.get("id")==Long.parseLong(id)){
				    	  jsonObj.put("posts", objects);
				    	  jsonObj.put("status",true);
				    	  jsonObj.put("statusCode",200);
				    	  return jsonObj;
				      }     
				}
				jsonObj.put("status",false);
		    	jsonObj.put("statusCode",404);
				jsonObj.put("message","Data Provided is Invalid");
			} catch (FileNotFoundException ex) {
				jsonObj.put("status",false);
		    	  jsonObj.put("statusCode",404);
				jsonObj.put("error", "File Not found");
	            ex.printStackTrace();
	            
	        } catch (IOException ex) {
	        	jsonObj.put("status",false);
		    	  jsonObj.put("statusCode",500);
	        	jsonObj.put("error", "Something Went Wrong");
	            ex.printStackTrace();
	        } catch (Exception ex) {
	        	jsonObj.put("status",false);
		    	 jsonObj.put("statusCode",500);
	        	jsonObj.put("error", "Something Went Wrong");
	            ex.printStackTrace();
	        }
			
	        return jsonObj;
}

	@GetMapping(value="/posts")
	public JSONObject getAllPosts(HttpServletRequest request) {
		JSONObject jsonObj = new JSONObject();
		String title=request.getParameter("title");
		String author=request.getParameter("author");
		String _sort=request.getParameter("_sort");
		String _order=request.getParameter("_order");
		String q=request.getParameter("q");
		try {
			 JSONParser parser = new JSONParser();
	         Object obj = parser.parse(new FileReader(filePath));
	         JSONObject jsonObject = (JSONObject) obj;
	         JSONArray posts = (JSONArray)jsonObject.get("posts");
	         if(title!=null && author!=null) {
	        	 JSONArray jsArray = new JSONArray();
	        	 for(int i = 0; i < posts.size(); i++)
					{
					      JSONObject objects = (JSONObject)posts.get(i);
					      if(objects.get("title").equals(title) && objects.get("author").equals(author)){
					    	  jsArray.add(objects);
					      }     
					}
	        	 if(jsArray.size()>0) {
	        		 jsonObj.put("status",true);
			    	 jsonObj.put("statusCode",200);
	        		 jsonObj.put("posts", jsArray);
	        		 return jsonObj;
	        	 }
	        	 else {
	        		 jsonObj.put("message","Data Provided is Invalid");
	        		 jsonObj.put("status",false);
			    	  jsonObj.put("statusCode",404);
	     	        return jsonObj;
	        	 }
	         }
	         else {
	        	 if(q !=null) {
	        		 JSONArray jsArray = new JSONArray();
		        	 for(int i = 0; i < posts.size(); i++)
						{
						      JSONObject objects = (JSONObject)posts.get(i);
						      String titleAuthor=(String)objects.get("title")+objects.get("author");
						      if(titleAuthor.indexOf(q) !=-1){
						    	  jsArray.add(objects);
						      }     
						}
		        	 if(jsArray.size()>0) {
		        		 jsonObj.put("status",true);
				    	  jsonObj.put("statusCode",200);
		        		 jsonObj.put("posts", jsArray);
		        		 return jsonObj;
		        	 }
		        	 else {
		        		 jsonObj.put("message","No Data is present");
		        		 jsonObj.put("status",true);
				    	  jsonObj.put("statusCode",200);
		     	        return jsonObj;
		        	 }
	        	 }
	        	 if(_sort != null && _order !=null) {
	        		 if(_order.equals("desc") && (_sort.equals("views") || _sort.equals("reviews") || _sort.equals("id")) ) {
	        			 JSONArray jsArray = new JSONArray();
	        			 List<JSONObject> jsonValues = new ArrayList<JSONObject>();
	        			    for (int i = 0; i < posts.size(); i++) {
	        			        jsonValues.add((JSONObject)posts.get(i));
	        			    }
	        			    Collections.sort( jsonValues, new Comparator<JSONObject>() {
	        			        private  final String KEY_NAME = _sort;
	        			        @Override
	        			        public int compare(JSONObject a, JSONObject b) {
	        			            long valA = 0;	
	        			            long valB = 0;

	        			            try {
	        			                valA = (long) a.get(KEY_NAME);
	        			                valB = (long) b.get(KEY_NAME);
	        			            } 
	        			            catch (Exception e) {
	        			            	// Do Something
	        			            }
	        			            return ( valA > valB ? -1 : (valA == valB ? 0 : 1 ) );
	        			       }
	        			    });
	        			    for(int i = 0; i < posts.size(); i++) {
	        			    	jsArray.add(jsonValues.get(i));
	        			      }
	    	        	 if(jsArray.size()>0) {
	    	        		 jsonObj.put("posts", jsArray);
	    	        		 jsonObj.put("status",true);
					    	  jsonObj.put("statusCode",200);
	    	        		 return jsonObj;
	    	        	 }
	    	        	 else {
	    	        		 jsonObj.put("message","Data Provided is Invalid");
	    	        		 jsonObj.put("status",false);
					    	  jsonObj.put("statusCode",404);
	    	     	        return jsonObj;
	    	        	 }
	        		 }
	        		 else {
	        			 if(_order.equals("asc") && (_sort.equals("views") || _sort.equals("reviews") || _sort.equals("id"))) {
	        				 JSONArray jsArray = new JSONArray();
		        			 List<JSONObject> jsonValues = new ArrayList<JSONObject>();
		        			    for (int i = 0; i < posts.size(); i++) {
		        			        jsonValues.add((JSONObject)posts.get(i));
		        			    }
		        			    Collections.sort( jsonValues, new Comparator<JSONObject>() {
		        			        private  final String KEY_NAME = _sort;
		        			        @Override
		        			        public int compare(JSONObject a, JSONObject b) {
		        			            long valA = 0;	
		        			            long valB = 0;

		        			            try {
		        			                valA = (long) a.get(KEY_NAME);
		        			                valB = (long) b.get(KEY_NAME);
		        			            } 
		        			            catch (Exception e) {
		        			            	// Do Something
		        			            }
		        			            return ( valA < valB ? -1 : (valA == valB ? 0 : 1 ) );
		        			       }
		        			    });
		        			    for(int i = 0; i < posts.size(); i++) {
		        			    	jsArray.add(jsonValues.get(i));
		        			      }
		    	        	 if(jsArray.size()>0) {
		    	        		 jsonObj.put("posts", jsArray);
		    	        		 jsonObj.put("status",true);
						    	  jsonObj.put("statusCode",200);
		    	        		 return jsonObj;
		    	        	 }
		    	        	 else {
		    	        		 jsonObj.put("message","Data Provided is Invalid");
		    	        		 jsonObj.put("status",false);
						    	  jsonObj.put("statusCode",404);
		    	     	        return jsonObj;
		    	        	 }
	        			 }
	        			 else {
	        				 jsonObj.put("message","Data Provided is Invalid");
	        				 jsonObj.put("status",false);
					    	  jsonObj.put("statusCode",404);
		    	     	        return jsonObj;
	        			 }
	        		 }
	        	 }
	         }
	         jsonObj.put("posts", posts);
	         jsonObj.put("status",true);
	    	  jsonObj.put("statusCode",200);
	         return jsonObj;
		} catch (FileNotFoundException ex) {
			jsonObj.put("error", "File Not found");
			jsonObj.put("status",false);
	    	  jsonObj.put("statusCode",404);
            ex.printStackTrace();
            
        } catch (IOException ex) {
        	jsonObj.put("error", "Something Went Wrong");
        	jsonObj.put("status",false);
	    	  jsonObj.put("statusCode",500);
            ex.printStackTrace();
        } catch (Exception ex) {
        	jsonObj.put("error", "Something Went Wrong");
        	jsonObj.put("status",false);
	    	  jsonObj.put("statusCode",500);
            ex.printStackTrace();
        }
		return jsonObj;

	}

	@DeleteMapping(value="/posts/{Id}")
	public JSONObject getDeletePost(@PathVariable("Id") String id) {
		JSONObject jsonObj = new JSONObject();
			try {
	            // read the json file
				 JSONParser parser = new JSONParser();
		         Object obj = parser.parse(new FileReader(filePath));
		         JSONObject jsonObject = (JSONObject) obj;
		         JSONArray posts = (JSONArray)jsonObject.get("posts");
				for(int i = 0; i < posts.size(); i++)
				{
				      JSONObject objects = (JSONObject)posts.get(i);
				      if((long)objects.get("id")==Long.parseLong(id)){
				    	 posts.remove(i);
				    	 FileWriter file = new FileWriter(filePath);
				         file.write(jsonObject.toJSONString());
				         file.close();
				    	 jsonObj.put("message","post succcessfully deleted");
				    	 jsonObj.put("status",true);
				    	  jsonObj.put("statusCode",200);
				    	 return jsonObj;
				      }     
				}
				jsonObj.put("message","Data Provided is Invalid");
				jsonObj.put("status",false);
		    	 jsonObj.put("statusCode",404);
				
			} catch (FileNotFoundException ex) {
				jsonObj.put("error", "File Not found");
				jsonObj.put("status",false);
		    	 jsonObj.put("statusCode",404);
	            ex.printStackTrace();
	            
	        } catch (IOException ex) {
	        	jsonObj.put("error", "Something Went Wrong");
	        	jsonObj.put("status",false);
		    	  jsonObj.put("statusCode",500);
	            ex.printStackTrace();
	        } catch (Exception ex) {
	        	jsonObj.put("error", "Something Went Wrong");
	        	jsonObj.put("status",false);
		    	  jsonObj.put("statusCode",500);
	            ex.printStackTrace();
	        }
			
	        return jsonObj;
}

	@PatchMapping(value="/posts/{Id}")
	public JSONObject updatePosts(@PathVariable("Id") String id,@RequestBody PostModel postModel) {
		JSONObject jsonObj = new JSONObject();
		try {
            // read the json file
			 JSONParser parser = new JSONParser();
	         Object obj = parser.parse(new FileReader(filePath));
	         JSONObject jsonObject = (JSONObject) obj;
	         JSONArray posts = (JSONArray)jsonObject.get("posts");
			for(int i = 0; i < posts.size(); i++)
			{
			      JSONObject objects = (JSONObject)posts.get(i);
			      if((long)objects.get("id")==Long.parseLong(id)){
			    	  objects.put("title", postModel.getTitle());
			    	  objects.put("author", postModel.getAuthor());
			    	  posts.set(i, objects);
					FileWriter file = new FileWriter(filePath);
					file.write(jsonObject.toJSONString());
					file.close();
					jsonObj.put("message", "post updated Successfully");
					jsonObj.put("status",true);
			    	 jsonObj.put("statusCode",200);
			    	 return jsonObj;
			      }     
			}
			jsonObj.put("message","Data Provided is Invalid");
			jsonObj.put("status",false);
	    	 jsonObj.put("statusCode",404);
			
		} catch (FileNotFoundException ex) {
			jsonObj.put("error", "File Not found");
			jsonObj.put("status",false);
	    	 jsonObj.put("statusCode",404);
            ex.printStackTrace();
            
        } catch (IOException ex) {
        	jsonObj.put("error", "Something Went Wrong");
        	jsonObj.put("status",false);
	    	 jsonObj.put("statusCode",500);
            ex.printStackTrace();
        } catch (Exception ex) {
        	jsonObj.put("error", "Something Went Wrong");
        	jsonObj.put("status",false);
	    	 jsonObj.put("statusCode",500);
            ex.printStackTrace();
        }
		
        return jsonObj;
	}

	@PutMapping(value="/authors/{Id}")
	public JSONObject updateAuthor(@PathVariable("Id") String id,@RequestBody AuthorModel authorModel) {
		JSONObject jsonObj = new JSONObject();
		try {
            // read the json file
			 JSONParser parser = new JSONParser();
	         Object obj = parser.parse(new FileReader(filePath));
	         JSONObject jsonObject = (JSONObject) obj;
	         JSONArray authors = (JSONArray)jsonObject.get("authors");
			for(int i = 0; i < authors.size(); i++)
			{
			      JSONObject objects = (JSONObject)authors.get(i);
			      if((long)objects.get("id")==Long.parseLong(id)){
			    	  objects.put("first_name", authorModel.getFirst_name());
			    	  objects.put("last_name", authorModel.getLast_name());
			    	  objects.put("posts", authorModel.getPosts());
			    	  authors.set(i, objects);
					FileWriter file = new FileWriter(filePath);
					file.write(jsonObject.toJSONString());
					file.close();
					jsonObj.put("message", "author updated Successfully");
					jsonObj.put("status",true);
			    	 jsonObj.put("statusCode",200);
			    	 return jsonObj;
			      }     
			}
			jsonObj.put("message","Data Provided is Invalid");
			jsonObj.put("status",false);
	    	 jsonObj.put("statusCode",404);
			
		} catch (FileNotFoundException ex) {
			jsonObj.put("error", "File Not found");
			jsonObj.put("status",false);
	    	 jsonObj.put("statusCode",404);
            ex.printStackTrace();
            
        } catch (IOException ex) {
        	jsonObj.put("error", "Something Went Wrong");
        	jsonObj.put("status",false);
	    	 jsonObj.put("statusCode",500);
            ex.printStackTrace();
        } catch (Exception ex) {
        	jsonObj.put("error", "Something Went Wrong");
        	jsonObj.put("status",false);
	    	 jsonObj.put("statusCode",500);
            ex.printStackTrace();
        }
		
        return jsonObj;	
	}


}


