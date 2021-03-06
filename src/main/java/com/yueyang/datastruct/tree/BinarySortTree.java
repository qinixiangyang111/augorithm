package com.yueyang.datastruct.tree;

/**
 * @program: augorithm
 * @description: 二叉排序树
 * @author: qinxiangyang
 * @create: 2020-05-25 20:54
 **/
public class BinarySortTree {


    public static class Node {
        int value;

        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }


        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }

        /**
         * 递归添加节点
         */

        public void add(Node node) {

            if (node == null) {
                return;
            }

            //判断传入节点的值和当前节点根节点的值关系
            if (node.value < this.value) {
                //添加的值小于当前节点的值
                if (this.left == null) {
                    this.left = node;
                } else {
                    //递归向左子树添加
                    this.left.add(node);
                }
            } else {
                //如果添加的值大于当前节点的值
                if (this.right == null) {
                    this.right = node;
                } else {
                    this.right.add(node);
                }
            }
        }


        /**
         * 中序遍历
         */
        public void preOrder() {
            if (this.left != null) {
                this.left.preOrder();
            }
            System.out.println(this);
            if (this.right != null) {
                this.right.preOrder();
            }

        }

        /**
         * 查找要删除的节点
         */
        public Node search(int value) {
            if (this.value == value) {
                return this;
            } else if (this.value > value) {
                //向左子树递归查找
                //如果左子节点为空，就不能再找了
                if (this.left == null) {
                    return null;
                }
                return this.left.search(value);
            } else {
                //向右子树
                if (this.right == null) {
                    return null;
                }
                return this.right.search(value);
            }
        }

        /**
         * 查找要删除的父节点
         */
        public Node searchParent(int value) {
            if ((this.left != null && this.left.value == value)
                    || (this.right != null && this.right.value == value)) {
                return this;
            } else {
                if (this.value > value && this.left != null) {
                    return this.left.searchParent(value);

                } else if (this.value <= value && this.right != null) {
                    return this.right.searchParent(value);
                } else {
                    return null;
                }

            }
        }
    }


    private Node root;

    /**
     * 添加节点
     *
     * @param node
     */
    public void add(Node node) {
        if (root == null) {
            //如果root为空
            root = node;
            return;
        }
        root.add(node);
    }

    /**
     * 遍历
     */
    public void infixOrder() {
        if (root == null) {
            System.out.println("二叉树为空");
            return;
        }
        root.preOrder();
    }


    /**
     * 查找要删除的节点
     */
    public Node search(int value) {
        if (root == null) {
            return null;
        }
        return root.search(value);

    }

    /**
     * 要删除查找父节点
     */
    public Node searchParent(int value) {
        if (root == null) {
            return null;
        }
        return root.searchParent(value);

    }

    /**
     * 返回
     * 删除
     * 两个功能
     *
     * @param node 传入的节点，（当做二叉树的根节点）
     * @return 返回以node为根节点二叉排序树的最小节点的值
     */
    public int delRightTreeMin(Node node) {
        Node targer = node;
        //循环查找左节点，就会找到最小值
        while (targer.left != null) {
            targer = targer.left;
        }
        //这时targer指向最小节点
        //删除最小节点
        deleteNode(targer.value);
        return targer.value;

    }

    /**
     * 获取根节点
     */
    public Node getRoot() {
        return root;
    }
    /*
    删除节点
     */

    public void deleteNode(int value) {
        if (root == null) {
            System.out.println("删除失败，空二叉树无法删除");
            return;
        } else {
            Node node = search(value);
            if (node == null) {
                //没找到，删除不了
                return;
            }
            //如果找到的是根节点，且只有一个节点
            if (root.value == value && root.left == null && root.right == null) {

                root = null;
                return;
            }
            /**
             * 找到父节点，这里就不可能为空了，因为已经排除根节点的情况
             */
            Node parentNode = searchParent(value);

            //删除叶子节点
            if (node.left == null && node.right == null) {
                if (parentNode.left != null && parentNode.left.value == value) {
                    parentNode.left = null;
                } else if (parentNode.right != null && parentNode.right.value == value) {
                    parentNode.right = null;
                }

            } else if (node.left != null && node.right != null) {
                //表示有左右子树
                int min = delRightTreeMin(node.right);
                node.value = min;
            } else {
                //删除 只有一个子树的节点
                //如果要删除的节点有左子节点
                if (node.left != null) {
                    if (parentNode != null) {

                        // 如果targetNode是parent的左子节点， parent.left=targetNode.left;
                        if (parentNode.left.value == value) {
                            parentNode.left = node.left;
                        } else {
                            parentNode.right = node.left;
                        }
                    } else {
                        root = node.left;
                    }
                } else {
                    //说明要删除的节点是有右子节点
                    //,如果targetNode有右子节点
                    if (parentNode != null) {
                        if (parentNode.left.value == value) {
                            parentNode.left = node.right;
                        } else {
                            parentNode.right = node.right;
                        }
                    }else{
                        root=node.right;
                    }

                }

            }

        }

    }

}