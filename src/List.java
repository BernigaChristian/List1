import javax.swing.tree.AbstractLayoutCache;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * @author : Christian Berniga
 * @class : 4 D
 * @created : 24/04/2022, domenica
 **/

class ListException extends Exception {
    private String error;

    public ListException(String error) {
        this.error=error;
    }

    @Override
    public String toString() {
        return error;
    }
}

public class List {
    private Node head;
    private int elements;

    public List() {
        head = null;
        elements = 0;
    }

    /*METODO ES 31 PAG 204
    * Scrivere un costruttore per una lista che la inizializzi con la fusione ORDINATA
    * degli elementi di due liste forniti come argomento e i cui elementi sono
    * originalmente ordinata in maniera crescente
    * */
    public List(List list1, List list2) throws ListException{
        Node tempList1 = list1.head, tempList2 = list2.head;
        while (tempList1 != null){
            add(tempList1.getInfo());
            tempList1 = tempList1.getLink();
            elements ++;
        }
        while (tempList2 != null){
            add(tempList2.getInfo());
            tempList2 = tempList2.getLink();
            elements ++;
        }
    }

    public int getElements() {
        return elements;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void add(int info)   throws ListException{
        if(isEmpty())   headAdd(info);
        else if(getElements() == 1)
            if(info > head.getInfo())   tailAdd(info);
            else    headAdd(info);
        else if(info < head.getInfo())  headAdd(info);
        else {
            Node temp = head;
            int index = 1;
            while (temp.getLink() != null  && !(temp.getInfo() <= info && temp.getLink().getInfo() >= info)){
                temp = temp.getLink();
                index ++;
            }
            if(temp.getLink() == null)    tailAdd(info);
            else    positionAdd(info, index + 1);
        }
    }

    public void headAdd(int info) {
        Node newNode = new Node(info);
        newNode.setLink(head);
        head = newNode;
        elements ++;
    }

    public void tailAdd(int info) {
        if(isEmpty()) headAdd(info);
        else {
            Node newNode = new Node(info);
            Node temp = head;
            while(temp.getLink() != null) {
                temp = temp.getLink();
            }
            temp.setLink(newNode);
            elements ++;
        }
    }

    public void positionAdd(int info, int index) throws ListException{
        Node temp = head;
        Node newNode = new Node(info);
        int i = 1;
        if(index < 1 || index > (elements + 1))
            throw new ListException("INVALID POSITION");
        if(isEmpty() || index==1)   headAdd(info);
        else if(index == (elements+1))  tailAdd(info);
        else {
            while(i ++ < index-1)
                temp = temp.getLink();
            newNode.setLink(temp.getLink());
            temp.setLink(newNode);
            elements ++;
        }
    }

    public void headRemove() throws ListException {
        if(isEmpty())
            throw new ListException("EMPTY LIST");
        head = head.getLink();
        elements --;
    }

    public void tailRemove() throws ListException {
        Node prevNode = head, nextNode = head;
        if(isEmpty())
            throw new ListException("EMPTY LIST");
        while(prevNode.getLink() != null) {
            nextNode = prevNode;
            prevNode = prevNode.getLink();
        }
        if(prevNode == head)    headRemove();
        else {
            nextNode.setLink(null);
            elements --;
        }
    }

    public void positionRemove(int index) throws ListException {
        Node prevNode = head, nextNode = head;
        int i = 0;
        if(isEmpty())
            throw new ListException("EMPTY LIST");
        if(index < 1 || index > (elements+1))
            throw new ListException("INVALID POSITION");
        if(index == 1)
            headRemove();
        else if(index == elements)
            tailRemove();
        else {
            while(prevNode.getLink() !=null && i ++ < index - 1) {
                nextNode = prevNode;
                prevNode = prevNode.getLink();
            }
            nextNode.setLink(prevNode.getLink());
            elements --;
        }
    }

    /*METODO ES 32 PAG 204
     * Scrivere un metodo denominato ERASE che ricerchi e rimuova tutte le occorrenze
     * nella lista dei nodi contenenti l'informazione fornita come parametro. Il metodo
     * deve restituire il numero dei nodi rimossi
     * */
    public int erase(int parameter) throws ListException{
        int erased = 0, index = 1;
        Node temp = head;
        while (temp != null){
            if(temp.getInfo() ==  parameter)    {
                positionRemove(index);
                erased++;
            }
            index ++;
            temp = temp.getLink();
        }
        return erased;
    }

    public String toString() {
        Node temp = head;
        String list = "list: \n";
        if(isEmpty())   list = " EMPTY LIST.\n ";
        while(temp != null) {
            list += temp.getInfo() + "\t";
            temp = temp.getLink();
        }
        return list;
    }
}

class Test{
    public static void main(String[] args) {
        List l = new List();
        List l2 =  new List();
        ArrayList<Integer> listInfo = new ArrayList<>();
        for(int i = 0; i < 20; i ++)
            listInfo.add(new Random().nextInt(100));
        System.out.println("RANDOM LIST INFO\n" + listInfo);
        try{
            for(int i = 0; i < 10; i ++){
                l.add(listInfo.get(i));
                l2.add(listInfo.get(i+10));
            }

            System.out.println("1° " + l);
            System.out.println("2° " + l2);

            List l3 = new List(l, l2);
            System.out.println("MERGED " + l3);

            l3.erase(listInfo.get(new Random().nextInt(listInfo.size())));
            System.out.println("TEST ERASE " + l3);

        }catch (Exception e){System.out.println(e);}
    }
}