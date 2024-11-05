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

    public Node bstSearch(int key) {
        return treeSearch(root, key);
    }

    private Node treeSearch(Node x, int key) {
        if (x == null || x.key == key) {
            return x;
        }
        if (key < x.key) {
            return treeSearch(x.left, key);
        } else {
            return treeSearch(x.right, key);
        }
    }

    public Node min(Node x) {
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    public Node max(Node x) {
        while (x.right != null) {
            x = x.right;
        }
        return x;
    }

    public Node treeSuccessor(Node x) {
        if (x.right != null) {
            return min(x.right);
        }
        Node y = x.p; // y is x's parent
        while (y != null && x == y.right) {
            x = y;
            y = y.p;
        }
        return y;
    }

    public Node bstInsert(int key) {
        return treeInsert(root, key);
    }

    public Node treeInsert(Node x, int key) {
        Node z = new Node(key);
        Node y = null;

        while (x != null) {
            y = x;
            if (z.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        z.p = y;
        if (y == null) {
            root = z;
        } else if (z.key < y.key) {
            y.left = z;
        } else {
            y.right = z;
        }

        return z;
    }

    public void transplant(Node u, Node v) {
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

    public void treeDelete(Node z) {
        if (z.left == null) {
            transplant(z, z.right);
        } else if (z.right == null) {
            transplant(z, z.left);
        } else {
            Node y = min(z.right); // y is z's successor
            if (y.p != z) {
                transplant(y, y.right);
                y.right = z.right;
                y.right.p = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.p = y;
        }
    }

    public void inorderWalk() {
        inorderWalk(root);
        System.out.println();
    }

    public void inorderWalk(Node x) {
        if (x != null) {
            inorderWalk(x.left);
            System.out.print(x.key + " ");
            inorderWalk(x.right);
        }
    }

    public static void main(String[] args) {
        BST bst = new BST();

        bst.bstInsert(15);
        bst.bstInsert(6);
        bst.bstInsert(18);
        bst.bstInsert(3);
        bst.bstInsert(7);
        bst.bstInsert(17);
        bst.bstInsert(20);
        bst.bstInsert(2);
        bst.bstInsert(4);

        // Inorder traversal
        System.out.print("Inorder walk: ");
        bst.inorderWalk();

        // Search for a key
        int key = 7;
        Node foundNode = bst.bstSearch(key);
        System.out.println("Search for " + key + ": " + (foundNode != null ? "Found" : "Not Found"));

        // Find the minimum and maximum elements
        System.out.println("Min: " + bst.min(bst.root).key);
        System.out.println("Max: " + bst.max(bst.root).key);

        // Find the successor of a given node
        System.out.println("Successor of 15: " + (bst.treeSuccessor(bst.bstSearch(15)).key));

        // Delete a node
        bst.treeDelete(bst.bstSearch(6));
        System.out.println("Inorder walk after deleting 6: ");
        bst.inorderWalk();
    }
}
