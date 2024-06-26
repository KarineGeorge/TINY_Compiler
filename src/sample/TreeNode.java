package sample;

class TreeNode {
    private String label;
    private String shape;
    private TreeNode left;
    private TreeNode right;
    private TreeNode middle;
    private int id;
    static private int staticId = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TreeNode(String label, String shape) {
        id = staticId;
        staticId++;
        this.label = label;
        this.shape=shape;
        this.left = null;
        this.right = null;
    }

    public TreeNode(String label,String shape, TreeNode left, TreeNode right) {

        id = staticId;
        staticId++;
        this.label = label;
        this.shape=shape;
        this.left = left;
        this.right = right;
    }
    public TreeNode(String label, String shape, TreeNode left, TreeNode middle, TreeNode right ) {
        id = staticId;
        staticId++;
        this.label = label;
        this.shape = shape;
        this.left = left;
        this.right = right;
        this.middle = middle;
    }

    // Getters and setters for elseBranch
    public TreeNode getmiddle() {
        return middle;
    }

    public String getLabel() {
        return label;
    }

    public String getShape() {
        return shape;
    }

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void printTree() {
        printTree(this, 0);
    }

    private void printTree(TreeNode node, int indent) {
        if (node != null ) {
            if(node.label != null){
                System.out.print(getIndentString(indent));
                System.out.println(node.getLabel()+" :"+node.getShape());
                printTree(node.getLeft(), indent + 1);
                printTree(node.getmiddle(),indent + 1);
                printTree(node.getRight(), indent + 1);
            }
            else {
                printTree(node.getLeft(), indent );
                printTree(node.getmiddle(),indent );
                printTree(node.getRight(), indent );
            }

        }
    }



    private String getIndentString(int indent) {
        StringBuilder indentString = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            indentString.append("  "); // Two spaces per level, adjust as needed
        }
        return indentString.toString();
    }
}