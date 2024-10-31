class Node {
    int key;
    Node left, right, p;
    int height; // height for AVL implementation

    public Node(int key) {
        this.key = key;
        this.left = this.right = this.p = null;
    }
}

public class BST {
    Node root;

    public BST() {
        root = null;
    }

    public Node search(int key) {
        return treeSearch(root, key);
    }

    private Node treeSearch(Node node, int key) {
        if (node == null || node.key == key) {
            return node;
        }
        if (key < node.key) {
            return treeSearch(node.left, key);
        } else {
            return treeSearch(node.right, key);
        }
    }

    public Node min(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public Node max(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    public Node treeSuccessor(Node node) {
        if (node.right != null) {
            return min(node.right);
        }
        Node parent = node.p;
        while (parent != null && node == parent.right) {
            node = parent;
            parent = parent.p;
        }
        return parent;
    }

    public Node treeInsert(int key) {
        Node newNode = new Node(key);
        Node parent = null;
        Node current = root;

        while (current != null) {
            parent = current;
            if (newNode.key < current.key) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        newNode.p = parent;
        if (parent == null) {
            root = newNode;
        } else if (newNode.key < parent.key) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        return newNode;
    }

    private void transplant(Node u, Node v) {
        if (u.p == null) {
            root = v;
        } else if (u == u.p.left) {
            u.p.left = v;
        } else {
            u.p.right = v;
        }
        if (v != null) {
            v.p = u.p;
        }
    }

    public void treeDelete(Node node) {
        if (node.left == null) {
            transplant(node, node.right);
        } else if (node.right == null) {
            transplant(node, node.left);
        } else {
            Node successor = min(node.right);
            if (successor.p != node) {
                transplant(successor, successor.right);
                successor.right = node.right;
                successor.right.p = successor;
            }
            transplant(node, successor);
            successor.left = node.left;
            successor.left.p = successor;
        }
    }

    public void inorderWalk() {
        inorderWalk(root);
        System.out.println();
    }

    public void inorderWalk(Node node) {
        if (node != null) {
            inorderWalk(node.left);
            System.out.print(node.key + " ");
            inorderWalk(node.right);
        }
    }

    public static void main(String[] args) {
        BST bst = new BST();

        bst.treeInsert(15);
        bst.treeInsert(6);
        bst.treeInsert(18);
        bst.treeInsert(3);
        bst.treeInsert(7);
        bst.treeInsert(17);
        bst.treeInsert(20);
        bst.treeInsert(2);
        bst.treeInsert(4);

        // Inorder traversal
        System.out.print("Inorder walk: ");
        bst.inorderWalk();

        // Search for a key
        int key = 7;
        Node foundNode = bst.search(key);
        System.out.println("Search for " + key + ": " + (foundNode != null ? "Found" : "Not Found"));

        // Find the minimum and maximum elements
        System.out.println("Min: " + bst.min(bst.root).key);
        System.out.println("Max: " + bst.max(bst.root).key);

        // Find the successor of a given node
        System.out.println("Successor of 15: " + (bst.treeSuccessor(bst.search(15)).key));

        // Delete a node
        bst.treeDelete(bst.search(6));
        System.out.println("Inorder walk after deleting 6: ");
        bst.inorderWalk();
    }
}
