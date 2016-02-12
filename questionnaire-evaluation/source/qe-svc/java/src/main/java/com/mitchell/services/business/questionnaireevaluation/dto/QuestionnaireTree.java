package com.mitchell.services.business.questionnaireevaluation.dto;

import org.apache.log4j.Logger;



public class QuestionnaireTree extends Tree<Question> {
   
	private static final long serialVersionUID = 1L;
	private static final String CLASS_NAME = QuestionnaireTree.class
	.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
	public QuestionnaireTree() {
        super();
    }

    public Node<Question> findNode(Node<Question> node, QuestionRqRsDto keyNode) {
    	if(LOGGER.isInfoEnabled()){
			LOGGER.info(CLASS_NAME +"  findNode" );
		}
    	if (node == null) {
            return null;
        }
        if (keyNode == null) {
            for (Node<Question> child : node.getChildren()) {
                if (child.getData().getLevelNumber() == 1
                        && child.getData().getSiblingOrder() == 1) {
                    return child;
                }
            }
        }else if (keyNode.getQustnnreQustnId() == node.getData().getQustnnreQustnId()) {
            return node;
        }else {
            Node<Question> cnode = null;
            for (Node<Question> child : node.getChildren()){
                if ((cnode = findNode(child, keyNode)) != null) {
                    return cnode;
                }
            }
                

        }
        return null;
    }

    
    public Node<Question> findSibling(Node<Question> currentNode) {
    
        if(currentNode !=null){
            Node<Question>    parentNode = currentNode.getParent();
            Question currntQustn = currentNode.getData();
            int siblingOrder = currntQustn.getSiblingOrder();
            if (parentNode != null
                    && parentNode.getNumberOfChildren() > siblingOrder) {
                for (Node<Question> childNode : parentNode.getChildren()) {
                    Question childQustn = childNode.getData();
                    if (childQustn.getAncstrAnswerItemId() == currntQustn
                                                 .getAncstrAnswerItemId()
                            && childQustn.getSiblingOrder() == (siblingOrder + 1)) {
                                          return childNode;
                                   }
                             }
                        
            }
             Node<Question> cnode = null;
                if ((cnode = findSibling(parentNode)) != null) {
                                 return cnode;
                          }
        }
                   
                  return null;
    }

    public Node<Question> findQuestOnSelectedAnswer(Node<Question> node,
            Answer keyNode) {
        if (node == null) {
            return null;
        }

        if (keyNode.getAnswerItemID() == node.getData()
                .getAncstrAnswerItemId()
                && keyNode.getQustnnerQustnId() == node
                        .getData().getAncstrQustnnrQustnId()) {
            if (node.getData().getSiblingOrder() == 1) {
                return node;
            }
        } else {
            Node<Question> cnode = null;
            for (Node<Question> child : node.getChildren()){
                if ((cnode = findQuestOnSelectedAnswer(child, keyNode)) != null) {
                    return cnode;
                }
            }
                

        }
        return null;
    }

}