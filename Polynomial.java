// Tapanga Witt
// CIS 2353
// Summer 2025
// Project 2

public class Polynomial {
    
    private Node head;
    
    // No-argument constructor. Sets the polynomial's linked chain head node to null
    public Polynomial() {
        head = null;
    }
    
    // Constructor that takes a string representing a polynomial
    public Polynomial(String poly) {
        head = null;
        parsePolynomial(poly);
    }
    
    // Copy constructor. Performs a deep copy of the otherPoly
    public Polynomial(Polynomial otherPoly) {
        head = null;
        if (otherPoly.head != null) {
            Node current = otherPoly.head;
            while (current != null) {
                addTerm(current.coefficient, current.exponent);
                current = current.nextNode;
            }
        }
    }
    
    // Helper method to parse a polynomial string
    private void parsePolynomial(String poly) {
        // Remove all spaces
        poly = poly.replaceAll("\\s+", "");
        
        // Handle empty string
        if (poly.isEmpty()) {
            return;
        }
        
        // Add a '+' at the beginning if the polynomial doesn't start with a sign
        if (poly.charAt(0) != '+' && poly.charAt(0) != '-') {
            poly = "+" + poly;
        }
        
        int i = 0;
        while (i < poly.length()) {
            // Read the sign
            int sign = 1;
            if (poly.charAt(i) == '+') {
                sign = 1;
                i++;
            } else if (poly.charAt(i) == '-') {
                sign = -1;
                i++;
            }
            
            // Read coefficient
            int coefficient = 0;
            boolean hasCoefficient = false;
            while (i < poly.length() && Character.isDigit(poly.charAt(i))) {
                coefficient = coefficient * 10 + (poly.charAt(i) - '0');
                hasCoefficient = true;
                i++;
            }
            
            // If no coefficient was read, it's implicitly 1
            if (!hasCoefficient) {
                coefficient = 1;
            }
            
            coefficient *= sign;
            
            // Read exponent
            int exponent = 0;
            if (i < poly.length() && poly.charAt(i) == 'x') {
                i++; // skip 'x'
                exponent = 1; // default exponent is 1
                
                if (i < poly.length() && poly.charAt(i) == '^') {
                    i++; // skip '^'
                    exponent = 0;
                    while (i < poly.length() && Character.isDigit(poly.charAt(i))) {
                        exponent = exponent * 10 + (poly.charAt(i) - '0');
                        i++;
                    }
                }
            }
            
            // Add the term to the polynomial
            if (coefficient != 0) {
                addTerm(coefficient, exponent);
            }
        }
    }
    
    // Helper method to add a term to the polynomial in the correct position
    private void addTerm(int coefficient, int exponent) {
        // If the polynomial is empty or the new term has higher exponent than head
        if (head == null || exponent > head.exponent) {
            Node newNode = new Node(coefficient, exponent);
            newNode.nextNode = head;
            head = newNode;
            return;
        }
        
        // If the new term has the same exponent as head, combine coefficients
        if (exponent == head.exponent) {
            head.coefficient += coefficient;
            // If coefficient becomes 0, remove the node
            if (head.coefficient == 0) {
                head = head.nextNode;
            }
            return;
        }
        
        // Find the correct position to insert the new term
        Node current = head;
        while (current.nextNode != null && current.nextNode.exponent > exponent) {
            current = current.nextNode;
        }
        
        // If we found a term with the same exponent, combine coefficients
        if (current.nextNode != null && current.nextNode.exponent == exponent) {
            current.nextNode.coefficient += coefficient;
            // If coefficient becomes 0, remove the node
            if (current.nextNode.coefficient == 0) {
                current.nextNode = current.nextNode.nextNode;
            }
        } else {
            // Insert the new term
            Node newNode = new Node(coefficient, exponent);
            newNode.nextNode = current.nextNode;
            current.nextNode = newNode;
        }
    }
    
    // The print method prints out the polynomial
    public void print() {
        if (head == null) {
            System.out.println("0");
            return;
        }
        
        Node current = head;
        boolean first = true;
        
        while (current != null) {
            // Print sign (except for the first term if it's positive)
            if (!first || current.coefficient < 0) {
                if (current.coefficient > 0) {
                    System.out.print("+");
                } else {
                    System.out.print("-");
                }
            }
            
            // Print coefficient (if it's not 1 or if exponent is 0)
            int absCoeff = Math.abs(current.coefficient);
            if (absCoeff != 1 || current.exponent == 0) {
                System.out.print(absCoeff);
            }
            
            // Print variable and exponent
            if (current.exponent > 0) {
                System.out.print("x");
                if (current.exponent > 1) {
                    System.out.print("^" + current.exponent);
                }
            }
            
            current = current.nextNode;
            first = false;
        }
        System.out.println();
    }
    
    // Static method that adds two polynomials and returns a new polynomial
    public static Polynomial add(Polynomial poly1, Polynomial poly2) {
        Polynomial result = new Polynomial();
        
        Node current1 = poly1.head;
        Node current2 = poly2.head;
        
        // Merge the two polynomials
        while (current1 != null || current2 != null) {
            if (current1 == null) {
                // Only poly2 has remaining terms
                result.addTerm(current2.coefficient, current2.exponent);
                current2 = current2.nextNode;
            } else if (current2 == null) {
                // Only poly1 has remaining terms
                result.addTerm(current1.coefficient, current1.exponent);
                current1 = current1.nextNode;
            } else if (current1.exponent > current2.exponent) {
                // poly1 term has higher exponent
                result.addTerm(current1.coefficient, current1.exponent);
                current1 = current1.nextNode;
            } else if (current2.exponent > current1.exponent) {
                // poly2 term has higher exponent
                result.addTerm(current2.coefficient, current2.exponent);
                current2 = current2.nextNode;
            } else {
                // Same exponent, combine coefficients
                int sumCoeff = current1.coefficient + current2.coefficient;
                if (sumCoeff != 0) {
                    result.addTerm(sumCoeff, current1.exponent);
                }
                current1 = current1.nextNode;
                current2 = current2.nextNode;
            }
        }
        
        return result;
    }
}
