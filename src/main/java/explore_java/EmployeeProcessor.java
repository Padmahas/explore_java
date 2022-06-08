/**
 * 
 */
package explore_java;

/**
 * @author padmahasa
 *
 */
/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.Comparator;
import java.util.function.Predicate;
import java.lang.Iterable;
import java.time.chrono.IsoChronology;



public class EmployeeProcessor {

    interface CheckEmployee {
        boolean test(Employee p);
    }

    // Approach 1: Create Methods that Search for Employees that Match One
    // Characteristic

    public static void printEmployeesOlderThan(List<Employee> roster, int age) {
        for (Employee p : roster) {
            if (p.getAge() >= age) {
                p.printEmployee();
            }
        }
    }

    // Approach 2: Create More Generalized Search Methods

    public static void printEmployeesWithinAgeRange(
        List<Employee> roster, int low, int high) {
        for (Employee p : roster) {
            if (low <= p.getAge() && p.getAge() < high) {
                p.printEmployee();
            }
        }
    }

    // Approach 3: Specify Search Criteria Code in a Local Class
    // Approach 4: Specify Search Criteria Code in an Anonymous Class
    // Approach 5: Specify Search Criteria Code with a Lambda Expression

    public static void printEmployees(
        List<Employee> roster, CheckEmployee tester) {
        for (Employee p : roster) {
            if (tester.test(p)) {
                p.printEmployee();
            }
        }
    }
    
    public static void printEmployeesWithLocalClass(
            List<Employee> roster) {
    	class CheckEmployeeClass {
            public boolean test(Employee p) {
                 return p.getGender() == Employee.Sex.MALE
                     && p.getAge() >= 18
                     && p.getAge() <= 25;
             }
         }
    	CheckEmployeeClass tester = new CheckEmployeeClass();
            for (Employee p : roster) {
                if (tester.test(p)) {
                    p.printEmployee();
                }
            }
        }
    
    // Approach 6: Use Standard Functional Interfaces with Lambda Expressions

    public static void printEmployeesWithPredicate(
        List<Employee> roster, Predicate<Employee> tester) {
        for (Employee p : roster) {
            if (tester.test(p)) {
                p.printEmployee();
            }
        }
    }

    // Approach 7: Use Lambda Expressions Throughout Your Application

    public static void processEmployees(
        List<Employee> roster,
        Predicate<Employee> tester,
        Consumer<Employee> block) {
        for (Employee p : roster) {
            if (tester.test(p)) {
                block.accept(p);
            }
        }
    }

    // Approach 7, second example

    public static void processEmployeesWithFunction(
        List<Employee> roster,
        Predicate<Employee> tester,
        Function<Employee, String> mapper,
        Consumer<String> block) {
        for (Employee p : roster) {
            if (tester.test(p)) {
                String data = mapper.apply(p);
                block.accept(data);
            }
        }
    }
    
    // Approach 8: Use Generics More Extensively

    public static <X, Y> void processElements(
        Iterable<X> source,
        Predicate<X> tester,
        Function<X, Y> mapper,
        Consumer<Y> block) {
            for (X p : source) {
                if (tester.test(p)) {
                    Y data = mapper.apply(p);
                    block.accept(data);
                }
            }
    }

    public static void main(String... args) {

        List<Employee> roster = Employee.createRoster();

        for (Employee p : roster) {
            p.printEmployee();
        }

        // Approach 1: Create Methods that Search for Employees that Match One
        // Characteristic

        System.out.println("Employees older than 20:");
        printEmployeesOlderThan(roster, 20);
        System.out.println();

        // Approach 2: Create More Generalized Search Methods

        System.out.println("Employees between the ages of 14 and 30:");
        printEmployeesWithinAgeRange(roster, 14, 30);
        System.out.println();

        // Approach 3: Specify Search Criteria Code in a Local Class

        System.out.println("Employees who are eligible for Selective Service:");

        class CheckEmployeeEligibleForSelectiveService implements CheckEmployee {
           public boolean test(Employee p) {
                return p.getGender() == Employee.Sex.MALE
                    && p.getAge() >= 18
                    && p.getAge() <= 25;
            }
        }

        printEmployees(
            roster, new CheckEmployeeEligibleForSelectiveService());

        System.out.println("Another way of Local class - Employees who are eligible for Selective Service:");
        printEmployeesWithLocalClass(roster);

        System.out.println();

        // Approach 4: Specify Search Criteria Code in an Anonymous Class

        System.out.println("Employees who are eligible for Selective Service " +
            "(anonymous class):");

        printEmployees(
            roster,
            new CheckEmployee() {
                public boolean test(Employee p) {
                    return p.getGender() == Employee.Sex.MALE
                        && p.getAge() >= 18
                        && p.getAge() <= 25;
                }
            }
        );

        System.out.println();

        // Approach 5: Specify Search Criteria Code with a Lambda Expression

        System.out.println("Employees who are eligible for Selective Service " +
            "(lambda expression):");

        printEmployees(
            roster,
            (Employee p) -> p.getGender() == Employee.Sex.MALE
                && p.getAge() >= 18
                && p.getAge() <= 25
        );

        System.out.println();

        // Approach 6: Use Standard Functional Interfaces with Lambda
        // Expressions

        System.out.println("Employees who are eligible for Selective Service " +
            "(with Predicate parameter):");

        printEmployeesWithPredicate(
            roster,
            p -> p.getGender() == Employee.Sex.MALE
                && p.getAge() >= 18
                && p.getAge() <= 25
        );

        System.out.println();

        // Approach 7: Use Lamba Expressions Throughout Your Application

        System.out.println("Employees who are eligible for Selective Service " +
            "(with Predicate and Consumer parameters):");

        processEmployees(
            roster,
            p -> p.getGender() == Employee.Sex.MALE
                && p.getAge() >= 18
                && p.getAge() <= 25,
            p -> p.printEmployee()
        );

        System.out.println();

        // Approach 7, second example

        System.out.println("Employees who are eligible for Selective Service " +
            "(with Predicate, Function, and Consumer parameters):");

        processEmployeesWithFunction(
            roster,
            p -> p.getGender() == Employee.Sex.MALE
                && p.getAge() >= 18
                && p.getAge() <= 25,
            p -> p.getEmailAddress(),
            email -> System.out.println(email)
        );

        System.out.println();

        // Approach 8: Use Generics More Extensively

        System.out.println("Employees who are eligible for Selective Service " +
            "(generic version):");

        processElements(
            roster,
            p -> p.getGender() == Employee.Sex.MALE
                && p.getAge() >= 18
                && p.getAge() <= 25,
            p -> p.getEmailAddress(),
            email -> System.out.println(email)
        );

        System.out.println();

        // Approach 9: Use Bulk Data Operations That Accept Lambda Expressions
        // as Parameters

        System.out.println("Employees who are eligible for Selective Service " +
            "(with bulk data operations):");

        roster
            .stream()
            .filter(
                p -> p.getGender() == Employee.Sex.MALE
                    && p.getAge() >= 18
                    && p.getAge() <= 25)
            .map(p -> p.getEmailAddress())
            .forEach(email -> System.out.println(email));
     }
}
