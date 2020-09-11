import bs4 as bs
import requests
import yfinance as yf
import pandas as pd
import datetime


def get_data():
    pd.set_option('display.max_columns', None)
    IndexList = ["^DJI", "^GSPC", "^IXIC", "000001.SS"]
    for index in IndexList:
        ticker = yf.Ticker(index)
        hist = ticker.history(period="2d")['Close']
        print(round((hist[1] - hist[0]) / hist[0] * 100, 2))


if __name__ == '__main__':
    get_data()
