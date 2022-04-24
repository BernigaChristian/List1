/**
 * @author : Christian Berniga
 * @class : 4 D
 * @created : 24/04/2022, domenica
 **/

public class Node {
    private int info;
    private Node link;

    public Node(int info) {
        this.info = info;
        link=null;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public Node getLink() {
        return link;
    }

    public void setLink(Node link) {
        this.link = link;
    }

    @Override
    public String toString(){
        return "[" + info+ "]";
    }
}