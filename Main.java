import java.lang.reflect.ParameterizedType;

/**
 * Created by markj on 2018-10-17.
 */
public class Main {
    public static void main(String args[]){

        ControllImpl con = new ControllImpl(); // 1번 이벤트에 대해 묶음 처리
        ControllImpl2 con2 = new ControllImpl2(); // 2번 이벤트에 대해 묶음 처리

        Pro pro = new Pro();
        pro.bind(con);
        pro.bind(con2);

        /**사용자의 이벤트 발생!!! 동시에 두가지 이벤트가 들어옴*/
        Keyboard keyboard = new Keyboard(); // 사용자 키보드 이벤트 들어옴
        Sound sound = new Sound(); // 사용자의 소리 이벤트 들어옴

        //실행부
        con.put(keyboard);
        System.out.println("-------------------------------------");
        con2.put(sound);
    }
}
class Control<EventType>{
    public Pro pro;
    public EventType event;
    public String getTypeName(){
		// System.out.println(event.getClass().getName() + event.getClass().toString());
        return event.getClass().toString();
    }
    public void put(EventType eventType){
        this.event = eventType;
        pro.scan(this, event);
    }
}

class Keyboard {
    public void print(String str){
        System.out.println(str);
    }
}

class Sound {
    public void what(String str){
        System.out.println("소리? : "+str);
    }
}

class ControllImpl extends Control<Keyboard>{

}

class ControllImpl2 extends Control<Sound>{

}

//자기가 처리하고 싶은 이벤트에 대해서 무한대로 등록함
class Pro{
    PlugIn[] plugIns = {new Case1(), new Case2(), new Case3(), new Case4(), new Case5(), new Case6()};

    public void bind(Control con){
        con.pro = this;
    }

    public void scan(Control con, Object object){

        for(int i=0; i<plugIns.length; i++) {
            if (plugIns[i].getTypeName().equals(con.getTypeName())){
                plugIns[i].plug(object);
            }
        }
    }
}



abstract class PlugIn<EventType>{
    String name;
    protected PlugIn(){
        name = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0].toString();
    }

    public String getTypeName(){
        return name;
    }

    public abstract void plug(EventType eventType);
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////

//키보드를 처리하는 방법1
class Case1 extends PlugIn<Keyboard>{
    @Override
    public void plug(Keyboard event) {
        event.print("키보드 첫번째 방법");
    }
}

//키보드를 처리하는 또다른 방법
class Case2 extends PlugIn<Keyboard>{
    @Override
    public void plug(Keyboard event) {
        event.print("키보드 두번째 방법");
    }
}

//여기서 부터는 소리를 처리
class Case3 extends PlugIn<Sound>{
    @Override
    public void plug(Sound event) {
        event.what("개다");
    }
}


class Case4 extends PlugIn<Sound>{
    @Override
    public void plug(Sound event) {
        event.what("소다");
    }
}

class Case5 extends PlugIn<Sound>{
    @Override
    public void plug(Sound event) {
        event.what("고양이다");
    }
}

class Case6 extends PlugIn<Sound>{
    @Override
    public void plug(Sound event) {
        event.what("말이다");
    }
}



