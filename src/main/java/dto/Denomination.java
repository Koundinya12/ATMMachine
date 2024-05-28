package dto;

/**
 * DTO for holding the withdrawal information based on the amount provided
 * @param denomination
 * @param noOfNotes
 */
public record Denomination(int denomination,int noOfNotes) {
}
