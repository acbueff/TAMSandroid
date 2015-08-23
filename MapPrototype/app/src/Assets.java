// Assets Collection for TAMS
// created by Arash PArnia for TAMS

public class Assets implements IObservable {

    private AssetsCollection assetscollection;

    public Assets() {
    observerlist = new LinkedList<IObserver>();
    assetscollection = new assetsCollection();
    }
     
    public Iterator<Object> getIterator() {
    return gwoc.iterator();
     
    }
     
    @Override
    public void addObserver(IObserver obs) {
    observerlist.add(obs);
    }
     
    @Override
    public void notifyObservers() {
    Proxy proxy = new Proxy(this);
    for (IObserver o : observerlist)
        o.update((IObservable) proxy, null);
    }
     
    public void resetWorld() {
    setLives(3);
    resetTime();
    resetScore();
    initLayout();
    notifyObservers();
    }
     
    public void initLayout() {
    setSweeperExists(false);
     
    gwoc.clear();
    Snake thesnake = makeSnake(Color.CYAN, 0, 2);
    thesnake.translate(window.getCenterX(), window.getCenterY());
    thesnake.scale(2, 2);
    gwoc.add(thesnake);
    // weasel
    Weasel weasel1 = makeWeasel(Color.RED, randomHeading(), 2);
    weasel1.translate(randomX(), randomY());
    //weasel1.scale(3, 3);
    weasel1.setStrategy(new WeaselBounceStrategy(weasel1, window));
    gwoc.add(weasel1);
     
    Weasel weasel2 = makeWeasel(Color.RED, randomHeading(), 3);
    weasel2.translate(randomX(), randomY());
    //weasel2.scale(3, 3);
    weasel2.setStrategy(new WeaselBounceStrategy(weasel2, window));
    gwoc.add(weasel2);
     
    Weasel weasel3 = makeWeasel(Color.RED, randomHeading(), 4);
    weasel3.translate(randomX(), randomY());
    //weasel3.scale(3, 3);
    weasel3.setStrategy(new WeaselBounceStrategy(weasel3, window));
    gwoc.add(weasel3);
     
    Wall wall1 = makeWall(Color.CYAN);
    wall1.translate(randomX(), randomY());
    wall1.scale(300, 2);
    gwoc.add(wall1);
     
    Wall wall2 = makeWall(Color.CYAN);
    wall2.translate(randomX(), randomY());
    wall2.scale(3, 200);
    gwoc.add(wall2);
     
    Wall wall3 = makeWall(Color.CYAN);
    wall3.translate(randomX(), randomY());
    wall3.scale(3, 200);
    gwoc.add(wall3);
     
    Wall wall4 = makeWall(Color.CYAN);
    wall4.translate(randomX(), randomY());
    wall4.scale(500, 2);
    gwoc.add(wall4);
     
    // MONEY
    Money money1 = makeMoney(Color.MAGENTA);
    money1.translate(randomX(), randomY());
    money1.scale(2, 2);
    gwoc.add(money1);
     
    Money money2 = makeMoney(Color.MAGENTA);
    money2.translate(randomX(), randomY());
    money2.scale(2, 2);
    gwoc.add(money2);
     
    Money money3 = makeMoney(Color.MAGENTA);
    money3.translate(randomX(), randomY());
    money3.scale(3, 3);
    gwoc.add(money3);
     
    Money money4 = makeMoney(Color.MAGENTA);
    money4.translate(randomX(), randomY());
    money4.scale(3, 3);
    gwoc.add(money4);
     
    // FOOD
    Food food1 = makeFood(Color.green);
    food1.translate(randomX(), randomY());
    food1.scale(3, 3);
    gwoc.add(food1);
    Food food2 = makeFood(Color.green);
    food2.translate(randomX(), randomY());
    food2.scale(3, 3);
    gwoc.add(food2);
    Food food3 = makeFood(Color.green);
    food3.translate(randomX(), randomY());
    food3.scale(3, 3);
    gwoc.add(food3);
    Food food4 = makeFood(Color.green);
    food4.translate(randomX(), randomY());
    food4.scale(3, 3);
    gwoc.add(food4);
     
    // BIRD
    Bird bird1 = makeBird(Color.blue, randomHeading(), 2, 1);
    bird1.translate(randomX(), randomY());
    gwoc.add(bird1);
     
    Bird bird2 = makeBird(Color.blue, randomHeading(), 2, 2);
    bird2.translate(randomX(), randomY());
    bird2.scale(-1, 1);
    gwoc.add(bird2);
     
    if (!isSweeperExists()) makeSweeper(null);
     
    }
     
    // factory methods
    private Wall makeWall(java.awt.Color color) {
    return new Wall(color);
    }
     
    private Money makeMoney(java.awt.Color color) {
    return new Money(color);
    }
     
    private Food makeFood(java.awt.Color color) {
    return new Food(color);
    }
     
    private Bird makeBird(java.awt.Color color, int heading, int speed, int size) {
    return new Bird(color, heading, speed, size);
    }
     
    private Weasel makeWeasel(java.awt.Color color, int heading, int speed) {
    return new Weasel(color, heading, speed);
    }
     
    private Snake makeSnake(java.awt.Color color, int heading, int speed) {
    return Snake.getinstance(color, heading, speed);
    }
     
    public void makeSweeper(Point2D p) {
    Sweeper sweeper1 = new Sweeper(Color.black, randomHeading(), 2, 2);
    if (sweepervecotr) sweeper1.setDrawControlPointVecotorVisiblility(true);
    else sweeper1.setDrawControlPointVecotorVisiblility(false);
    if (p == null) sweeper1.translate(randomX(), randomY());
    else sweeper1.translate(p.getX(), p.getY());
    gwoc.add(sweeper1);
    setSweeperExists(true);
    }
     
    public void killSweeper() {
    Iterator<Object> it = getIterator();
    while (it.hasNext()) {
        Object gw = it.next();
        if (gw instanceof Sweeper) {
        it.remove();
        setSweeperExists(false);
        break;
        }
    }
    notifyObservers();
    }
     
    public void changeDrawControlPointVecotorVisiblility() {
    Iterator<Object> it = getIterator();
    while (it.hasNext()) {
        Object gw = it.next();
        if (gw instanceof Sweeper) {
        if (sweepervecotr) {
            ((Sweeper) gw).setDrawControlPointVecotorVisiblility(false);
            sweepervecotr = false;
        }
        else {
            ((Sweeper) gw).setDrawControlPointVecotorVisiblility(false);
            sweepervecotr = true;
        }
        }
    }
    }
     
    public boolean isDrawSweeperControlVecotr() {
    return sweepervecotr;
    }
     
    // -------------------mutators for objects------------------------
    // ------------------------------------------------------------------------
    public void collideBody() {
    if (getLives() <= 1) resetWorld();
    else loseLives();
    System.out.println("colideBody lost 1 life");
    if (sound) bitesound.play();
    notifyObservers();
    }
     
    public void colideBird(Bird b) {
    // find a bird and remove it
    Iterator<Object> it = getIterator();
    while (it.hasNext()) {
        Object gw = it.next();
        if (gw instanceof Bird & gw == b) {
        it.remove();
        break;
        }
    }
    if (getLives() <= 1) resetWorld();
    else loseLives();
    System.out.println("colideBird lost 1 life");
    if (sound) hawksound.play();
    notifyObservers();
    }
     
    public void sweepBird(Bird b) {
    // find a bird and remove it
    Iterator<Object> it = getIterator();
    while (it.hasNext()) {
        Object gw = it.next();
        if (gw instanceof Bird & gw == b) {
        it.remove();
        break;
        }
    }
    if (sound) hawksound.play();
    notifyObservers();
    }
     
    public void colideWeasle(Weasel w) {
    // find a weasel and remove it
    Iterator<Object> it = getIterator();
    while (it.hasNext()) {
        Object gw = it.next();
        if (gw instanceof Weasel & gw == w) {
        it.remove();
        break;
        }
    }
    if (getLives() <= 1) resetWorld();
    else loseLives();
    System.out.println("colideWeasel lost 1 life");
    if (sound) cling2sound.play();
    notifyObservers();
    }
     
    public void sweepWeasle(Weasel w) {
    // find a weasel and remove it
    Iterator<Object> it = getIterator();
    while (it.hasNext()) {
        Object gw = it.next();
        if (gw instanceof Weasel & gw == w) {
        it.remove();
        break;
        }
    }
    notifyObservers();
    }
     
    public void colideMoney(Money m) {
    // find a money and add value to score
    Iterator<Object> it = getIterator();
    while (it.hasNext()) {
        Object gw = it.next();
        if (gw instanceof Money & gw == m) {
        score(m.getValue());
        it.remove();
        if (sound) coinsound.play();
        notifyObservers();
        break;
        }
    }
     
    }
     
    public void colideFood(Food f) {
    int amount = 0;
     
    // find a food! get the amount
    Iterator<Object> it = getIterator();
    while (it.hasNext()) {
        Object gw = it.next();
        if (gw instanceof Food & gw == f) {
        amount = f.getAmount();
        it.remove();
        Snake thesnake = findTheSnake();
        // increase the bodysegment count
        thesnake.setSegmentCount(amount);
        if (sound) cling1sound.play();
        notifyObservers();
        break;
        }
    }
     
    }
     
    public void colideWall() {
    if (getLives() <= 1) resetWorld();
    else loseLives();
    System.out.println("colideWall lost 1 life");
    if (sound) cling2sound.play();
    notifyObservers();
    }
     
    private Snake findTheSnake() {
    Iterator<Object> it = getIterator();
    Snake thesnake = null;
    while (it.hasNext()) {
        Object gw = it.next();
        if (gw instanceof Snake) {
        thesnake = ((Snake) gw);
        }
    }
    return thesnake;
    }
     
    // -------------------------mutators for lives-------------------------
    // ------------------------------------------------------------------------
    public int getLives() {
    return lives;
    }
     
    // no one should have this power!
    private void setLives(int lives) {
    this.lives = lives;
    }
     
    // its public so the player can cheat!
    public void addlives() {
    this.lives = this.lives + 1;
    notifyObservers();
    }
     
    // same here
    public void loseLives() {
    this.initLayout();
    this.lives = this.lives - 1;
     
    }
     
    // -------------------------mutators for
    // time---------------------------------------------------
    public String getTime() {
    time = new SimpleDateFormat("mm:ss.SSS").format(date);
    return time;
    }
     
    public void resetTime() {
    date = new Date((long) (0000));
     
    }
     
    public void tick(int delay) {
    Vector<ICollider> collisionvectorcollider = new Vector<ICollider>();
    Vector<ICollider> collisionvectorcollidy = new Vector<ICollider>();
    date.setTime(date.getTime() + 100);
    move(delay);
    if (date.getTime() % 10000 == 0) {
        switchStrategy();
        if (isSweeperExists()) killSweeper();
        else makeSweeper(null);
    }
    if (date.getTime() > 100) {
         
        Iterator<Object> i = getIterator();
         
        while (i.hasNext()) {
        Object collider = i.next();
        Iterator<Object> j = getIterator();
        if (collider instanceof ICollider) {
            ICollider thecollider = (ICollider) collider;
            while (j.hasNext()) {
            Object collidy = j.next();
            if (collidy instanceof ICollider) {
                 
                ICollider thecollidy = (ICollider) collidy;
                 
                if (thecollider.collidesWith(thecollidy) & thecollider != thecollidy) {
                if (((collider instanceof Sweeper) || (collider instanceof Snake)) && !collisionvectorcollider.contains(thecollider)) {
                    collisionvectorcollider.add(thecollider);
                    if (!collisionvectorcollidy.contains(thecollidy)) {
                    collisionvectorcollidy.add(thecollidy);
                     
                    //thecollider.handleCollision(thecollidy, this);
                     
                    }
                     
                }
                }
            }
            if (collider instanceof Snake) {
                ((Snake) collider).checkCollisionWithBody(this);
            }
             
            }
        }
         
        }
        for (int k = 0; k < collisionvectorcollidy.size(); k++)
        collisionvectorcollider.get(k).handleCollision(collisionvectorcollidy.get(k), this);
        //System.out.println(collisionvectorcollider.get(k).getClass().getName() + "  " + collisionvectorcollidy.get(k).getClass().getName());
    }
    }
     
    // moves objects giggle
    private void move(int delay) {
    Iterator<Object> it = getIterator();
    while (it.hasNext()) {
        Object gw = it.next();
        if (gw instanceof MovableObject) {
        MovableObject m = (MovableObject) gw;
        m.move(delay);
        }
    }
    notifyObservers();
    }
     
    // gives directions to turn the tip of the snake ;))
    public void north() {
    findTheSnake().north();
    notifyObservers();
    }
     
    public void east() {
    findTheSnake().east();
    notifyObservers();
    }
     
    public void south() {
    findTheSnake().south();
    notifyObservers();
    }
     
    public void west() {
    findTheSnake().west();
    notifyObservers();
    }
     
    // -----------------------------------mutators for
    // score--------------------------------------
    public int getScore() {
    return score;
    }
     
    public void resetScore() {
    this.score = 0;
    }
     
    public void score(int amount) {
    this.score = this.score + amount;
    }
     
    // random generator
    private float randomX() {
    Random random = new Random();
    int Low = 50;
    int High = (int) window.getMaxX() - 50;
    int randomvalue = random.nextInt(High - Low) + Low;
    return (float) (randomvalue);
    }
     
    private float randomY() {
    Random random = new Random();
    int Low = 50;
    int High = (int) window.getMaxY() - 50;
    int randomvalue = random.nextInt(High - Low) + Low;
    return (float) (randomvalue);
    }
     
    private int randomHeading() {
    Random random = new Random();
    int Low = 0;
    int High = 359;
    int randomvalue = random.nextInt(High - Low) + Low;
    return randomvalue;
    }
     
    public boolean getSound() {
    return sound;
    }
     
    public void setSound(boolean s) {
    this.sound = s;
    if (!sound) dnbsound.stop();
    else dnbsound.loop();
    notifyObservers();
    }
     
    public void switchStrategy() {
    for (Object o : gwoc) {
        if (o instanceof Weasel) {
        Weasel w = (Weasel) o;
         
        if (w.getCurrentStrategy() instanceof WeaselBounceStrategy) {
            Snake thesnake = null;
            for (Object sn : gwoc) {
            if (sn instanceof Snake) {
                thesnake = (Snake) sn;
            }
            }
            w.setStrategy(new WeaselFollowStrategy(w, thesnake));
        }
        else {
            w.setStrategy(new WeaselBounceStrategy(w, window));
        }
         
        System.out.println("STRATEGY IS SET TO" + w.getCurrentStrategy().getClass().getName());
        }
         
    }
    }
     
    public void deleteSelected() {
     
    Iterator<Object> i = getIterator();
    Object o;
    while (i.hasNext()) {
        o = i.next();
        if (o instanceof ISelectable) {
        ISelectable is = (ISelectable) o;
        if (is.isSelected()) {
            i.remove();
        }
        }
    }
    notifyObservers();
     
    }
     
    public boolean isPaused() {
    return paused;
    }
     
    public void setPaused(boolean paused) {
    this.paused = paused;
    }
     
    public boolean isSweeperExists() {
    return sweeperExists;
    }
     
    public void setSweeperExists(boolean sweeperExists) {
    this.sweeperExists = sweeperExists;
    }
     
    // -------------------- DATA STRUCTURE------------------
    private class AssetsCollection implements Collection<Object> {
    private Vector<Object> objects;
     
    public ObjectsCollection() {
        objects = new Vector<Object>();
    }
     
    @Override
    public int size() {
        return objects.size();
    }
     
    @Override
    public boolean isEmpty() {
        return objects.isEmpty();
    }
     
    @Override
    public boolean contains(Object o) {
        return objects.contains(o);
    }
     
    @Override
    public Iterator<Object> iterator() {
        // return objects.iterator();
        return new Iterator();
    }
     
    @Override
    public Object[] toArray() {
        return objects.toArray();
    }
     
    @Override
    public <T> T[] toArray(T[] a) {
        return objects.toArray(a);
    }
     
    @Override
    public boolean add(Object e) {
        return objects.add(e);
    }
     
    @Override
    public boolean remove(Object o) {
        return objects.remove(o);
    }
     
    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }
     
    @Override
    public boolean addAll(Collection<? extends Object> c) {
        return false;
    }
     
    @Override
    public boolean removeAll(Collection<?> c) {
         
        return objects.removeAll(c);
    }
     
    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }
     
    @Override
    public void clear() {
        objects.clear();
        // TODO Auto-generated method stub
         
    }
     
    private class Iterator implements Iterator<Object> {
        private int index;
         
        public Iterator() {
        index = -1;
        }
         
        @Override
        public boolean hasNext() {
        if (objects.size() <= 0) return false;
        if (index == objects.size() - 1 || index == objects.size()) return false;
        return true;
        }
         
        @Override
        public Object next() {
        index = index + 1;
        return (objects.elementAt(index));
        }
         
        @Override
        public void remove() {
        objects.remove(index);
        }
    }
    }
     
}
