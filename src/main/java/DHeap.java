// DHeap class
//
// CONSTRUCTION: with optional capacity (that defaults to 100)
//               or an array containing initial items
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// Comparable deleteMin( )--> Return and remove smallest item
// Comparable findMin( )  --> Return smallest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

import java.util.Arrays;

/**
 * Implements a binary heap.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class DHeap<AnyType extends Comparable<? super AnyType>>
{
    private static final int DEFAULT_CAPACITY = 10;

    private int currentSize;      // Number of elements in heap
    private AnyType [ ] array; // The heap array
    private int children;

    /**
     * Construct a binary heap.
     */
    public DHeap()
    {
        this(2);
    }
    /**
     * Construct the d-heap.
     * @param children the number of children for each node in the d-heap.
     *                 or throws IllegalArgumentException if num of children < 2
     */
    public DHeap(int children) throws IllegalArgumentException
    {
        if(children<2)
            throw new IllegalArgumentException("parameter children to dheap must be > 1");
        this.children = children;
        currentSize = 0;
        array = (AnyType[]) new Comparable[ DEFAULT_CAPACITY + 1 ];
    }


    /**
     * Construct the binary heap given an array of items.
     */
    public DHeap( AnyType [ ] items )
    {
        currentSize = items.length;
        array = (AnyType[]) new Comparable[ ( currentSize + 2 ) * 11 / 10 ];

        int i = 1;
        for( AnyType item : items )
            array[ i++ ] = item;
        buildHeap( );
    }

    @Override
    public String toString() {
        return "DHeap{" +
                "array=" + Arrays.toString(array) +
                '}';
    }

    /**
     * Insert into the priority queue, maintaining heap order.
     * Duplicates are allowed.
     * @param x the item to insert.
     */
    public void insert( AnyType x )
    {
        if(isEmpty()){
            array[1] = x;
            currentSize++;
            return;
        }
        if( currentSize == array.length - 1 )
            enlargeArray( array.length * 2 + 1 );

        // Percolate up
        int hole = currentSize++;
        array[0] = x;
        System.out.println(this.toString());
        AnyType lastCurrentNode = get(hole-1);
        while(x.compareTo(lastCurrentNode) < 0){
                array[hole] = lastCurrentNode;
                lastCurrentNode = last

        }
        while(lastCurrentNode!=null && x.compareTo(lastCurrentNode)<0){
            int parentIndex;
            try{
                parentIndex = parentIndex(hole);
            }catch(IllegalArgumentException iae){
                break;
            }
            array[hole] = array[parentIndex];
            hole = parentIndex(hole);
            lastCurrentNode = get(parentIndex);

        }
        array[hole] = lastCurrentNode;
        System.out.println("after"+this.toString());

//        for( array[ 0 ] = x; x.compareTo( get(parentIndex(hole)) ) < 0; hole = parentIndex(hole) )
//            array[ hole ] = array[ hole / 2 ];
//        array[ hole ] = x;
    }


    private void enlargeArray( int newSize )
    {
        AnyType [] old = array;
        array = (AnyType []) new Comparable[ newSize ];
        for( int i = 0; i < old.length; i++ )
            array[ i ] = old[ i ];
    }

    /**
     * Find the smallest item in the priority queue.
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return array[ 1 ];
    }

    /**
     * Remove the smallest item from the priority queue.
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType deleteMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );

        AnyType minItem = findMin( );
        System.out.println(minItem);
        array[ 1 ] = array[ currentSize-- ];
        percolateDown(1);
         return minItem;
    }
    /**
     * Internal method to percolate down in the heap.
     * @param hole the index at which the percolate begins.
     */
    private void percolateDown( int hole )
    {
        int child;
        AnyType tmp = array[ hole ];
        while (firstChildIndex(hole) < size())
        {
            child = minChildIndex(hole);
            if (array[child].compareTo(tmp)<0)
                array[hole] = array[child];
            else
                break;
            hole = child;
        }
//        for( ; minChildIndex(hole) <= currentSize; hole = child )
//        {
//            child = minChildIndex(hole);//hole * 2;
//            if( child != currentSize &&
//                    array[ child + 1 ].compareTo( array[ child ] ) < 0 )
//                child++;
//            if( array[ child ].compareTo( tmp ) < 0 )
//                array[ hole ] = array[ child ];
//            else
//                break;
//        }
        array[ hole ] = tmp;
    }
    /**
      @param child noden vars förälder vi ska hitta
      throws illegalargumentexception om (child+children-2)/children < 1
            //notes to self
            exempel med 3-heap:
            Barn @ index 5: (5+3-2)/3 ger parent = 2 (korrekt)
            Barn @ index 6: (6+3-2)/3 = 2 (korrekt)
            Barn @ index 2: (2+3-2)/3 = 1 (korrekt)
            4-heap:
            Barn @ index 18: (18+4-2)/4 = 5(korrekt)
            Barn @ index 21: (21+4-2)/4 = 5(korrekt)
            Barn @ index 5: (5+4-2)/4 = 1 (korrekt)
    */
    public int parentIndex(int child) throws IllegalArgumentException{
        int parent = (child+children-2)/children;
        if(parent < 1)
            throw new IllegalArgumentException();
        return parent;
    }

    /**
    *@param parent, nodens vars första barn vi ska hitta
     * Note to self:
     * 4-heap:
     *              parent @ index 1 = (1-1)*4 +2 = 2(korrekt)
     *              parent @ index 5 = (5-1)*4 +2 = 18(korrekt)
     * 3-heap:
     *              parent @ index 2 = (2-1)*3 +2 = 5(korrekt)
    */
    public int firstChildIndex(int parent) throws IllegalArgumentException{
        int child = (parent-1) * children +2;
        if(child < 2 || child > array.length) //lägsta möjliga barn
            throw new IllegalArgumentException();
        return child;
    }
    private AnyType minChild(int parent){
        int firstChildIndex = firstChildIndex(parent);
        AnyType minChild = array[firstChildIndex];
        for(int i =1;i<children;i++){
            if(firstChildIndex+i <= size()){
                AnyType compareWith = array[firstChildIndex+i];
                if(compareWith !=null  && minChild.compareTo(compareWith) > 0)
                    minChild = compareWith;
            }
        }
        return minChild;
    }
    private int minChildIndex(int parent){
        int firstChildIndex = firstChildIndex(parent);
        AnyType minChild = array[firstChildIndex];
        int minChildIndex = firstChildIndex;
        for(int i =1;i<children;i++){
            if(firstChildIndex+i <= size()){
                AnyType compareWith = array[firstChildIndex+i];
                if(compareWith !=null  && minChild.compareTo(compareWith) > 0)
                    minChildIndex = firstChildIndex+i;
            }
        }
        return minChildIndex;
    }
    public int size() {
        return currentSize;
    }

    public AnyType get(int index) {
        return array[index];
    }

    /**
     * Establish heap order property from an arbitrary
     * arrangement of items. Runs in linear time.
     */
    private void buildHeap( )
    {
        for( int i = currentSize / 2; i > 0; i-- )
            percolateDown( i );
    }

    /**
     * Test if the priority queue is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return currentSize == 0;
    }

    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty( )
    {
        currentSize = 0;
    }

}
