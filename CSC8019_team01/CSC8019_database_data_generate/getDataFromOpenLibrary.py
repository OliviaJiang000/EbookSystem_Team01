import csv
import requests

INPUT_FILE = '/Users/jojo/Downloads/books.csv'
OUTPUT_FILE = 'books_with_images_google.csv'

def get_cover_url_from_isbn(isbn):
    if not isbn:
        return ''
    try:
        url = f"https://openlibrary.org/search.json?isbn={isbn}"
        resp = requests.get(url, timeout=5)
        if resp.status_code == 200:
            data = resp.json()
            docs = data.get("docs", [])
            if docs and 'cover_i' in docs[0]:
                cover_id = docs[0]['cover_i']
                return f"https://covers.openlibrary.org/b/id/{cover_id}-L.jpg"
    except requests.RequestException:
        pass
    return ''  # 如果没有找到或出错

def process_books():
    with open(INPUT_FILE, newline='', encoding='utf-8') as infile, \
         open(OUTPUT_FILE, 'w', newline='', encoding='utf-8') as outfile:

        reader = csv.DictReader(infile)
        fieldnames = reader.fieldnames + ['image_url']
        writer = csv.DictWriter(outfile, fieldnames=fieldnames)
        writer.writeheader()

        for row in reader:
            isbn = row.get('isbn', '').strip()
            row['image_url'] = get_cover_url_from_isbn(isbn)
            print(row['image_url'] )
            writer.writerow(row)

        print(f"处理完成，保存为 {OUTPUT_FILE}")

if __name__ == '__main__':
    process_books()
