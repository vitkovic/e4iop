export default class DateTimeUtils {
  public formatForDatetimeLocalInput(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are 0-based
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');

    return `${year}-${month}-${day}T${hours}:${minutes}`;
  }

  public getDateMonthsAgo(date: Date, months: number): Date {
    let result = new Date(date);
    result.setMonth(result.getMonth() - months);
    return result;
  }
}
