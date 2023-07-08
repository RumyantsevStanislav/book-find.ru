//public class NewInJava {
//            switch (day) {
//                case MONDAY, FRIDAY, SUNDAY -> System.out.println(6);
//                case TUESDAY                -> System.out.println(7);
//                case THURSDAY, SATURDAY     -> System.out.println(8);
//                case WEDNESDAY              -> System.out.println(9);
//            }
//            String longString = """
//                    loooooooooooooooooooong string
//                    """;
//            Optional;
//            String readString (Path);java.nio.file.Path
//            int numberOfLetters = switch (dayOfWeek) {
//                case MONDAY:
//                case FRIDAY:
//                case SUNDAY:
//                    yield 6;
//                case TUESDAY
//                        yield 7;
//                case THURSDAY
//                case SATURDAY
//                        yield 8;
//                case WEDNESDAY
//                        yield 9;
//                default:
//                    throw new IllegalStateException("Huh?: " + day);
//            };
//
//    /**
//     * класс, 3 int private final поля, конструктор, equals, hashCode, toString
//     * javac —enable-preview —release 14 Triangle.java
//     * Если не нужны методы в классе
//     * @param x
//     * @param y
//     * @param z
//     */
//    public record Triangle(int x, int y, int z){}
//
//    Object object = Violin;
//    if (object instanceof Instrument instrument){
//        //Instrument instrument = (Instrument) object; - теперь не нужно
//        System.out.println(instrument.getMaster());
//    }
//
//    /**
//     * sealed - ограничивает набор наследников
//     */
//    sealed class Person permits Student, Teacher, Curator {}
//    public sealed class Teacher extends Person permits MathTeacher, LanguageTeacher {}
//
//    /**
//     * non-sealed, когда для класса нужно снять любые ограничения по наследованию:
//     */
//    public non-sealed class Curator extends Person {}
//
//    /**
//     * У интерфейсов тоже может быть модификатор sealed
//     */
//    sealed interface Person
//            permits Student, Teacher, Curator {
//    }
//}
//
//
