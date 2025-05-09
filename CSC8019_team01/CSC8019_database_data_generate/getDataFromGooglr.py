import csv
import requests
import time

INPUT_FILE = '/Users/jojo/Downloads/books.csv'
OUTPUT_FILE = 'books_with_images_Google.csv'
MAX_COUNT = 199  # 最多处理 199 本书

def get_cover_url_google(isbn):
    if not isbn:
        return ''
    try:
        url = f'https://www.googleapis.com/books/v1/volumes?q=isbn:{isbn}'
        resp = requests.get(url, timeout=5)
        if resp.status_code == 200:
            data = resp.json()
            items = data.get('items', [])
            if items:
                image_links = items[0]['volumeInfo'].get('imageLinks', {})
                return image_links.get('thumbnail', '')
    except requests.RequestException as e:
        print(f"请求出错（ISBN: {isbn}）: {e}")
    return ''

def process_books():
    with open(INPUT_FILE, newline='', encoding='utf-8') as infile, \
         open(OUTPUT_FILE, 'w', newline='', encoding='utf-8') as outfile:

        reader = csv.DictReader(infile)
        fieldnames = reader.fieldnames + ['image_url']
        writer = csv.DictWriter(outfile, fieldnames=fieldnames)
        writer.writeheader()

        for i, row in enumerate(reader, 1):
            if i > MAX_COUNT:
                break
            isbn = row.get('isbn', '').strip()
            cover_url = get_cover_url_google(isbn)
            row['image_url'] = cover_url
            writer.writerow(row)
            print(f"{i}. 处理ISBN: {isbn} -> {'✅' if cover_url else '❌'}")
            time.sleep(0.2)

    print(f"\n处理完成，共处理 {MAX_COUNT} 本图书，已写入：{OUTPUT_FILE}")

if __name__ == '__main__':
    process_books()
