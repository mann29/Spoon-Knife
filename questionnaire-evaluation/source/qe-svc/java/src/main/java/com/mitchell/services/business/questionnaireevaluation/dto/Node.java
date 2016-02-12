package com.mitchell.services.business.questionnaireevaluation.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
* Represents a node of the Tree<T> class. The Node<T> is also a container, and
* can be thought of as instrumentation to determine the location of the type T
* in the Tree<T>.
*/
public class Node<T extends Serializable> implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private  T data;
   private List<Node<T>> children;
   private Node<T> parent;
   
    
   /**
    * Return the children of Node<T>. The Tree<T> is represented by a single
    * root Node<T> whose children are represented by a List<Node<T>>. Each of
    * these Node<T> elements in the List can have children. The getChildren()
    * method will return the children of a Node<T>.
    * @return the children of Node<T>
    */
   public List<Node<T>> getChildren() {
       if (this.children == null) {
           return new ArrayList<Node<T>>();
       }
       return this.children;
   }

   public Node<T> getParent() {
      return this.parent;
   }

   public void setParent(Node<T> parent) {
      this.parent = parent;
   }

   
   /**
    * Sets the children of a Node<T> object. See docs for getChildren() for
    * more information.
    * @param children the List<Node<T>> to set.
    */
   public void setChildren(List<Node<T>> children) {
       this.children = children;
   }

   /**
    * Returns the number of immediate children of this Node<T>.
    * @return the number of immediate children.
    */
   public int getNumberOfChildren() {
       if (children == null) {
           return 0;
       }
       return children.size();
   }
    
   /**
    * Adds a child to the list of children for this Node<T>. The addition of
    * the first child will create a new List<Node<T>>.
    * @param child a Node<T> object to set.
    */
   public void addChild(Node<T> child) {
       if (children == null) {
           children = new ArrayList<Node<T>>();
       }
       children.add(child);
   }

   public T getData() {
       return this.data;
   }

   public void setData(T data) {
       this.data = data;
   }
    
   public String toString() {
       StringBuilder sb = new StringBuilder();
       sb.append("{").append(getData().toString()).append(",[");
       int i = 0;
       for (Node<T> e : getChildren()) {
           if (i > 0) {
               sb.append(",");
           }
           sb.append(e.getData().toString());
           i++;
       }
       sb.append("]").append("}");
       return sb.toString();
   }


}