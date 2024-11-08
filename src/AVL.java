public class AVL extends BST {
    public AVL() {
        super();
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.p = x;
        }
        y.p = x.p;
        if (x.p == null) {
            root = y;
        } else if (x == x.p.left) {
            x.p.left = y;
        } else {
            x.p.right = y;
        }
        y.left = x;
        x.p = y;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        y.left = x.right;
        if (x.right != null) {
            x.right.p = y;
        }
        x.p = y.p;
        if (y.p == null) {
            root = x;
        } else if (y == y.p.right) {
            y.p.right = x;
        } else {
            y.p.left = x;
        }
        x.right = y;
        y.p = x;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    private void updateHeight(Node x) {
        if (x != null) {
            x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        }
    }

    private int getHeight(Node x) {
        return (x == null) ? -1 : x.height;
    }

    private int getBF(Node x) {
        return (x == null) ? 0 : getHeight(x.right) - getHeight(x.left);
    }

    private Node rebalance(Node x) {
        int balanceFactor = getBF(x);

        if (balanceFactor < -1) {
            if (getBF(x.left) > 0) {
                x.left = leftRotate(x.left);  // Left-Right
            }
            x = rightRotate(x);  // Left-Left
        }

        else if (balanceFactor > 1) {
            if (getBF(x.right) < 0) {
                x.right = rightRotate(x.right);  // Right-Left
            }
            x = leftRotate(x);  // Right-Right
        }

        return x;
    }

    public void insert(int key) {
        root = insert(root, key);
    }

    private Node insert(Node x, int key) {
        if (x == null) {
            return new Node(key);
        }

        if (key < x.key) {
            x.left = insert(x.left, key);
            x.left.p = x;
        } else if (key > x.key) {
            x.right = insert(x.right, key);
            x.right.p = x;
        } else {
            return x;
        }

        // Above shows a recursive BFT insertion; you can also
        // call BST-insert to implement AVL-insert.

        updateHeight(x);
        return rebalance(x);
    }

    public void delete(int key) {
        Node z = bstSearch(key);
        if (z == null) {
            return;
        }

        treeDeleteAndUpdateHeight(z);

        Node y = (z.p != null) ? z.p : root;

        while (y != null) {
            updateHeight(y);
            y = rebalance(y);
            y = y.p;  // Move up to rebalance
        }
    }

    public void treeDeleteAndUpdateHeight(Node z) {
        if (z.left == null) {
            transplant(z, z.right);
        } else if (z.right == null) {
            transplant(z, z.left);
        } else {
            Node y = min(z.right);  // y is the successor of z
            Node yp = null;
            if (y.p != z) {
                transplant(y, y.right);
                yp = y.p;
                y.right = z.right;
                y.right.p = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.p = y;

            while(yp != null) {
                updateHeight(yp);
                yp = rebalance(yp);
                yp = yp.p;
            }

            updateHeight(y);  // Update y's height
        }
    }

    public void inOrderWalkWithHeight() {
        System.out.print("AVL inorder walk with height: ");
        inOrderWalkWithHeight(root);
        System.out.println();
    }

    public void inOrderWalkWithHeight(Node node) {
        if (node != null) {
            inOrderWalkWithHeight(node.left);
            System.out.print("{v: " + node.key + " at h: " + node.height + "}; ");
            inOrderWalkWithHeight(node.right);
        }
    }


    public static void main(String[] args) {
        AVL avlTree = new AVL();

        // Below shows the lecture example
        System.out.println("Inserting elements: 25, 65, 9, 22, 3, 50, 21");
        avlTree.insert(25);
        avlTree.insert(65);
        avlTree.insert(9);
        avlTree.insert(22);
        avlTree.insert(3);
        avlTree.insert(50);
        avlTree.insert(21);

        avlTree.inOrderWalkWithHeight();
        System.out.println();

        System.out.println("Inserting elements: 17");
        avlTree.insert(17); // left-left

        avlTree.inOrderWalkWithHeight();
        System.out.println();

        System.out.println("Inserting elements: 55");
        avlTree.insert(55); // Zig-zag left-right

        avlTree.inOrderWalkWithHeight();
        System.out.println();

        System.out.println("Deleting element: 65");
        avlTree.delete(65);

        avlTree.inOrderWalkWithHeight();
        System.out.println();

        System.out.println("Deleting element: 55");
        avlTree.delete(55); // Zig-zag left-right

        avlTree.inOrderWalkWithHeight();
        System.out.println();
    }
}