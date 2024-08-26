export default class NumericDataUtils {
  public dottedInputFormatter(value: string | number): string {
    let newValue = '';
    if (typeof value === 'number') {
      newValue = value.toString();
    } else {
      newValue = value;
    }

    if (newValue == null) {
      return '';
    }

    newValue = newValue.replace(/\D/g, '');

    if (!newValue || newValue.length <= 3) {
      return newValue;
    }

    const parts: string[] = [];
    let remaining = newValue;

    while (remaining.length > 3) {
      // Take the last 3 characters
      const part = remaining.slice(-3);
      parts.unshift(part); // Add to the beginning of the array
      remaining = remaining.slice(0, -3); // Remove the last 3 characters
    }

    // Add the remaining part (less than 3 characters)
    parts.unshift(remaining);

    // Join the parts with dots
    return parts.join('.');
  }
}
