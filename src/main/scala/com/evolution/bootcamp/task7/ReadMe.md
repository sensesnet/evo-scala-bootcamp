# Scala Bootcamp HW


 Домашнее Задание
 Домашка:
 TypeclassTask в файле OopComparison
 файл Homework в typeclass.v2
 Вместо тестов пусть будет компилятор
 
 
 
 // having two implementations for the same type (like different ways to make json out of User) is possible
 // but considered to be bad
 
 object TypeclassTask {
 
   // Why am I not a Typeclass?
   // TODO: Rework me so I am a typeclass
   trait HashCode {
     def hash: Int
   }
 
   object HashCode {
     // TODO: Implement me a summoner
   }
 
   implicit class HashCodeSyntax[A](x: A) {
     // TODO: Implement syntax so I can "abc".hash
   }
 
   // TODO: make an instance for String
   // TODO: write "abc".hash to check everything
 }
